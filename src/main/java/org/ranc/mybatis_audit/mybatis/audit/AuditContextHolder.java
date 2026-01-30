package org.ranc.mybatis_audit.mybatis.audit;

public class AuditContextHolder {
    
    private static final ThreadLocal<AuditContext> transactionIdHolder =new ThreadLocal<>();

    private AuditContextHolder() {
    }

    public static void setTransactionId(AuditContext auditContext) {
        transactionIdHolder.set(auditContext);
    }

    public static AuditContext getTransactionId() {
        return transactionIdHolder.get();
    }

    public static void clear() {
        transactionIdHolder.remove();
    }

}
