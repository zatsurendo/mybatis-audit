package org.ranc.mybatis_audit.repository;

import org.apache.ibatis.annotations.Mapper;
import org.ranc.mybatis_audit.model.Revinfo;

@Mapper
public interface RevinfoMapper {
    
    Revinfo findByRev(Long rev);
    int newRev(Revinfo revinfo);
}
