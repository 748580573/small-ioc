package com.heng.ioc.factory;

public interface IOCFactory {

    Object getBean(String beanId);

    <T> T getBean(String beanId,Class<T> clazz);

    <T> T getBean(T clazz);
}
