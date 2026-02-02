package org.ranc.mybatis_audit.spring;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;
import org.ranc.mybatis_audit.mybatis.audit.plugin.AuditInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
public class ApplicationContextHolderTest {
    
    // テストクラスにApplicationContextをインジェクションする
    @Autowired
    private ApplicationContext injectedContext;
    
    @Test
    void getContext() {
        // 1. まず、テスト環境のSpringコンテナが正しく起動していることを確認
        assertNotNull(injectedContext, "ApplicationContext should be injected by Spring");

        // 2. ApplicationContextHolderから取得したcontextが、
        //    インジェクションされたものと同じインスタンスであることを確認する
        ApplicationContext holderContext = ApplicationContextHolder.getContext();
        assertNotNull(holderContext, "ApplicationContextHolder.getContext() should not return null");
        
        // 3. （より厳密に）両者が同じものであることを確認
        assertSame(injectedContext, holderContext, "Context from holder should be the same as injected context");
    }
    @Test
    public void testGetBean() {
        ApplicationContext context = ApplicationContextHolder.getApplicationContext();
        System.out.println("ApplicationContext: " + context);
        AuditInterceptor interceptor = ApplicationContextHolder.getBean("auditInterceptor", AuditInterceptor.class);
        System.out.println("AuditInterceptor Bean: " + interceptor);
    }
}
