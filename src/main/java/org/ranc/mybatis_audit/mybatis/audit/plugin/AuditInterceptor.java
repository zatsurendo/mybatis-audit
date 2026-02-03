package org.ranc.mybatis_audit.mybatis.audit.plugin;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collection;

import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.ranc.mybatis_audit.mybatis.audit.AuditContext;
import org.ranc.mybatis_audit.mybatis.audit.AuditContextHolder;
import org.ranc.mybatis_audit.mybatis.audit.annotation.HistoryPersistable;
import org.ranc.mybatis_audit.mybatis.audit.annotation.NoAudit;
import org.ranc.mybatis_audit.mybatis.audit.model.AuditAware;
import org.ranc.mybatis_audit.mybatis.audit.model.HistoryAware;
import org.ranc.mybatis_audit.mybatis.audit.model.VersionAware;
import org.ranc.mybatis_audit.spring.ApplicationContextHolder;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("auditInterceptor")
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })
})
public class AuditInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        AuditContext auditContext = AuditContextHolder.getTransactionId();
        if (auditContext == null) {
            // トランザクションIDが設定されていない場合は何もしない
            return invocation.proceed();
        }

        // MappedStatementからSQLの種類などを取得
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        // パラメータオブジェクトを取得
        Object parameter = invocation.getArgs()[1];
        // SQLの種類を取得
        SqlCommandType type = mappedStatement.getSqlCommandType();

        // パラメータオブジェクトがAuditAwareの実装クラスの場合、監査情報を設定する
        if (parameter instanceof AuditAware && !parameter.getClass().isAnnotationPresent(NoAudit.class)) {
            processAudit(parameter, auditContext, type);
            // クエリを実行
            Object result = invocation.proceed();
            if (type == SqlCommandType.INSERT) {
                // INSERTの場合は invocation.proceed() の後に履歴保存を行う
                if (!parameter.getClass().isAnnotationPresent(NoAudit.class)) {
                    // @NoAudit が付与されていなければ履歴を保存
                    processHistory(parameter, auditContext, type);
                }
            }
            return result;
        }

        // パラメータオブジェクトがParamMapの場合、値の中にAuditAwareの実装クラスが含まれているか確認する
        if (parameter instanceof MapperMethod.ParamMap) {
            MapperMethod.ParamMap<?> params = (MapperMethod.ParamMap<?>) parameter;
            Object obj = params.values().toArray()[0];
            if (obj instanceof Collection) {
                Collection<?> coll = (Collection<?>) obj;
                for (Object item : coll) {
                    processAudit(item, auditContext, type);
                }
                // クエリを実行
                Object result = invocation.proceed();
                
                if (type == SqlCommandType.INSERT) {
                    // INSERTの場合は invocation.proceed() の後に履歴保存を行う
                    for (Object item : coll) {
                        processHistory(item, auditContext, type);
                    }
                }
                return result;
            }
        }
        // 本来のクエリを実行
        return invocation.proceed();
    }

    protected void processAudit(Object parameter, AuditContext auditContext, SqlCommandType type)
            throws Throwable {
        
        // AuditAware を継承していて、@NoAudit が付与されていなければ監査情報を設定
        if (parameter instanceof AuditAware && !parameter.getClass().isAnnotationPresent(NoAudit.class)) {
            AuditAware auditAware = (AuditAware) parameter;
            setAuditFields(auditAware, auditContext, type);
            if (type != SqlCommandType.INSERT && parameter.getClass().isAnnotationPresent(HistoryPersistable.class)) {
                // INSERT 以外は履歴を保存
                saveHistoryRecord(parameter, auditContext, type);
            }
        }
    }

    protected void processHistory(Object parameter, AuditContext auditContext, SqlCommandType type)
            throws Throwable {
        
        Class<?> clazz = parameter.getClass();
        // HistoryPersistable を継承していて、@NoAudit が付与されていなければ履歴を保存
        if (clazz.isAnnotationPresent(HistoryPersistable.class) && !parameter.getClass().isAnnotationPresent(NoAudit.class)) {
            saveHistoryRecord(parameter, auditContext, type);
        }
    }

    protected void saveHistoryRecord(Object object, AuditContext auditContext, SqlCommandType type) {
        // 対象レコードのクラス名
        String className = object.getClass().getName();
        // 対象レコードのクラス名 + "Log" がログクラス名
        String logClassName = className + "Log";
        try {
            // ログクラス名からクラスを取得
            Class<?> logClass = Class.forName(logClassName);
            // ログクラスのインスタンスを生成
            Object logOjbect = logClass.getDeclaredConstructor().newInstance();
            // HistoryAwareの場合は、revtypeとrevを設定
            if (logOjbect instanceof HistoryAware) {
                int revtype = type == SqlCommandType.INSERT ? 1
                        : type == SqlCommandType.UPDATE ? 2 : type == SqlCommandType.DELETE ? 3 : 0;
                HistoryAware historyAware = (HistoryAware) logOjbect;
                historyAware.setRevtype(revtype);
                historyAware.setRev(auditContext.getRevinfo().getRev());
            }
            // コピー元オブジェクトのプロパティをログオブジェクトにコピー
            BeanUtils.copyProperties(object, logOjbect);

            // マッパークラスのクラス名を生成する 基底パス ＋ 元クラス名 ＋ "Mapper"
            String mapperClassName = "org.ranc.mybatis_audit.repository." + logClass.getSimpleName() + "Mapper";
            // マッパークラス名からクラスを取得
            Class<?> mapperClass = Class.forName(mapperClassName);
            // マッパークラスのinsertメソッドを取得して実行
            Method method = mapperClass.getMethod("insert", logClass);
            // マッパークラスのBean名を生成
            String beanName = Character.toLowerCase(mapperClass.getSimpleName().charAt(0)) +
                    mapperClass.getSimpleName().substring(1);
            // マッパークラスのBeanを取得
            Object instance = ApplicationContextHolder.getBean(beanName, mapperClass);
            // マッパーBean のメソッドを実行する
            Object result = method.invoke(instance, logOjbect);
            log.debug("Saved {} history record for {}: {}", result, className, logOjbect);
        } catch (Exception e) {
            // Nop
            throw new RuntimeException("Failed to save history record for " + className, e);
        }
    }

    protected void setAuditFields(AuditAware auditAware, AuditContext auditContext, SqlCommandType type) {
        OffsetDateTime ts = Instant.ofEpochMilli(auditContext.getRevinfo().getRevtstmp())
                .atOffset(OffsetDateTime.now().getOffset());
        String auditUser = auditContext.getRemoteUser() + "@" + auditContext.getRemoteHost();

        // 新規登録の場合のみ作成者情報を設定
        if (type == SqlCommandType.INSERT) {
            auditAware.setCreatedBy(auditUser);
            auditAware.setCreatedAt(ts);
        }
        // バージョン情報を設定
        if (auditAware instanceof VersionAware) {
            VersionAware vp = (VersionAware) auditAware;
            if (type != SqlCommandType.INSERT) {
                vp.setVersion(vp.getVersion() + 1);
            } else {
                vp.setVersion(1L);
            }
        }
        // 更新者情報を設定
        auditAware.setUpdatedBy(auditUser);
        auditAware.setUpdatedAt(ts);
        System.out.println("BindParamInterceptor intercepted AuditAware parameter in Collection: " + auditAware);
    }
}
