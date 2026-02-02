package org.ranc.mybatis_audit.mybatis.audit.model;

public interface HistoryAware {
    Long getRev();
    void setRev(Long rev);
    Integer getRevtype();
    void setRevtype(Integer revtype);
}
