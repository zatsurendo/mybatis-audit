package org.ranc.mybatis_audit.mybatis.audit.model;

public interface VersionAware {
    Long getVersion();
    void setVersion(Long version);
}
