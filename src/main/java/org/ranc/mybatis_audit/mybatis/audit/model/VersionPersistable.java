package org.ranc.mybatis_audit.mybatis.audit.model;

public interface VersionPersistable {
    Long getVersion();
    void setVersion(Long version);
    Integer getRevtype();
    void setRevtype(Integer revtype);
}
