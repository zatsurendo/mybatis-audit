package org.ranc.mybatis_audit.mybatis.audit.plugin;

import java.time.Instant;
import java.time.OffsetDateTime;

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
            OffsetDateTime ts = Instant.ofEpochMilli(auditContext.getRevinfo().getRevtstmp()).atOffset(OffsetDateTime.now().getOffset());
            String auditUser = auditContext.getRemoteUser() + "@" + auditContext.getRemoteHost();

            if (type == SqlCommandType.INSERT) {
                auditAware.setCreatedBy(auditUser);
                auditAware.setUpdatedBy(auditUser);
                auditAware.setCreatedAt(ts);
                auditAware.setUpdatedAt(ts);
            } else if (type == SqlCommandType.UPDATE) {
                auditAware.setUpdatedBy(auditUser);
                auditAware.setUpdatedAt(ts);
            }
        }
        Object o = invocation.proceed();
        return o;
    }
}
