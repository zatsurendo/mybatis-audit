package org.ranc.mybatis_audit.mybatis.audit.model;

public interface AuditAware extends UseTimestamp {
    
    String getCreatedBy();
    void setCreatedBy(String createdBy);
    String getUpdatedBy();
    void setUpdatedBy(String updatedBy);
}
