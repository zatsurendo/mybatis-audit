package org.ranc.mybatis_audit.mybatis;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.ranc.mybatis_audit.mybatis.audit.plugin.AuditInterceptor;
import org.ranc.mybatis_audit.spring.ApplicationContextHolder;
import org.ranc.mybatis_audit.spring.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import lombok.extern.log4j.Log4j2;

/**
 * @author zatsurendo
 * @date 2026-01-30 07:05:46
 */
@Log4j2
@Configuration
@MapperScan("org.ranc.mybatis_audit.repository")
@PropertySource(value = "classpath:dbsettings.yml", factory = YamlPropertySourceFactory.class)
public class MybatisInfraConfig {

    @Autowired
    Environment env;
    @Autowired
    ApplicationContextHolder applicationContextHolder;

    @Bean
    public DataSource h2Datasource() {

        BasicDataSource bean = new BasicDataSource();
        bean.setDriverClassName(env.getProperty("datasource.prod.driver-class-name"));
        bean.setUrl(env.getProperty("datasource.prod.url"));
        bean.setUsername(env.getProperty("datasource.prod.username"));
        bean.setPassword(env.getProperty("datasource.prod.password"));
        bean.setDefaultAutoCommit(Boolean.parseBoolean(env.getProperty("datasource.prod.auto-commit")));
        return bean;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource h2DataSource) throws Exception {

        AuditInterceptor auditInterceptor = ApplicationContextHolder.getBean("auditInterceptor", AuditInterceptor.class);
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(h2DataSource);
        factoryBean.setPlugins(new Interceptor[] { auditInterceptor });
        return factoryBean.getObject();
    }
}
