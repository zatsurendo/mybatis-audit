package org.ranc.mybatis_audit.mybatis.audit.model;

import java.io.Serializable;

public interface AuditAware extends UseTimestamp {
    
    Serializable getId();
    String getCreatedBy();
    void setCreatedBy(String createdBy);
    String getUpdatedBy();
    void setUpdatedBy(String updatedBy);
}
