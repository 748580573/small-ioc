package com.heng.ioc.factory.config;

import java.util.List;

public interface BeanDefinition {

    void setAttribute(String name,Object value);

    Object getAttribute(String name);

    void setBeanId(String beanId);

    String getBeanId();

    void setBeanClassName(String beanClassName);

    String getBeanClassName();

    void setDependsOn(String... dependsOn);

    List<String> getDependsOn();

    /**
     * set whether this bean is acandidate for getting autowired into some other bean.
     * @return
     */
    void setAutowireCandidate(boolean autowireCandidate);

    /**
     * return whether this bean is a candidate for getting autowired into some other bean.
     * @return
     */
    boolean isAutowireCandidate();
}
