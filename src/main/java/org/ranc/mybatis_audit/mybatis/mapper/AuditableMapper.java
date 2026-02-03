package org.ranc.mybatis_audit.mybatis.mapper;

public interface AuditableMapper<E> {
    int insert(E entity);
    int insertBatch(Iterable<E> entities);
    int update(E entity);
    int delete(E entity);
}
