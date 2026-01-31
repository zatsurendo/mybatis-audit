package org.ranc.mybatis_audit.mybatis.audit.plugin;

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
import org.ranc.mybatis_audit.mybatis.audit.model.AuditAware;
import org.ranc.mybatis_audit.mybatis.audit.model.VersionPersistable;
import org.springframework.stereotype.Component;

@Component("auditInterceptor")
@Intercepts({
    @Signature(
        type = Executor.class,
        method = "update",
        args = {MappedStatement.class, Object.class}
    )
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

        SqlCommandType type = mappedStatement.getSqlCommandType();

        if (parameter instanceof AuditAware) {
            AuditAware auditAware = (AuditAware) parameter;
            setAuditFields(auditAware, auditContext, type);
            return invocation.proceed();
        }
        if (parameter instanceof MapperMethod.ParamMap) {
             MapperMethod.ParamMap<?> params = (MapperMethod.ParamMap<?>) parameter;
            Object obj = params.values().toArray()[0];
            if (obj instanceof Collection) {
                Collection<?> coll = (Collection<?>) obj;
                for (Object item : coll) {
                    if (item instanceof AuditAware) {
                        AuditAware auditAware = (AuditAware) item;
                        setAuditFields(auditAware, auditContext, type);
                    }
                }
            }
            return invocation.proceed();
        }
        return invocation.proceed();
    }

    protected void setAuditFields(AuditAware auditAware, AuditContext auditContext, SqlCommandType type) {
        OffsetDateTime ts = Instant.ofEpochMilli(auditContext.getRevinfo().getRevtstmp())
                .atOffset(OffsetDateTime.now().getOffset());
        String auditUser = auditContext.getRemoteUser() + "@" + auditContext.getRemoteHost();

        if (type == SqlCommandType.INSERT) {
            // 新規登録の場合のみ作成者情報を設定
            auditAware.setCreatedBy(auditUser);
            auditAware.setCreatedAt(ts);
        }
        if (auditAware instanceof VersionPersistable) {
            VersionPersistable vp = (VersionPersistable) auditAware;
            int revtype = type == SqlCommandType.INSERT ? 1
                    : type == SqlCommandType.UPDATE ? 2
                    : type == SqlCommandType.DELETE ? 3
                    : 0;
            vp.setRevtype(revtype);
            if (type != SqlCommandType.INSERT) {
                vp.setVersion(vp.getVersion() + 1);
            } else {
                vp.setVersion(1L);
            }
        }
        auditAware.setUpdatedBy(auditUser);
        auditAware.setUpdatedAt(ts);
        System.out.println("BindParamInterceptor intercepted AuditAware parameter in Collection: " + auditAware);
    }
}
