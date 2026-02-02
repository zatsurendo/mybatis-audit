package org.ranc.mybatis_audit.spring;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(@NonNull  ApplicationContext ac) throws BeansException {
        context = ac;
    }

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * Beanを取得します。
     * @param <T> Beanの型
     * @param requiredType 取得したいBeanのクラス
     * @return Beanのインスタンス
     */
    public static <T> T getBean(Class<T> requiredType) {
        return context.getBean(requiredType);
    }

    /**
     * Beanを取得します。
     * @param <T> Beanの型
     * @param beanName 取得したいBeanの名前
     * @param requiredType 取得したいBeanのクラス
     * @return Beanのインスタンス
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return context.getBean(beanName, requiredType);
    }
}
