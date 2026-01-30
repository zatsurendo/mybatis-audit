package org.ranc.mybatis_audit.repository;

import java.time.Instant;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.model.Revinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RevinfoMapperTest {
    
    @Autowired
    private RevinfoMapper revinfoMapper;

    @Test
    public void test() {
        Instant now = Instant.now();
        Long revtstmp = now.toEpochMilli();
        
        Revinfo revinfo = new Revinfo();
        revinfo.setRevtstmp(revtstmp);
        revinfoMapper.newRev(revinfo);
        System.out.println(revinfo.toString());
        
        Revinfo fetched = revinfoMapper.findByRev(revinfo.getRev());
        assert fetched != null;
        assert fetched.getRev().equals(revinfo.getRev());
        assert fetched.getRevtstmp().equals(revinfo.getRevtstmp());
    }
}
