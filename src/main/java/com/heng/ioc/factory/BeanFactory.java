package com.heng.ioc.factory;

import com.heng.ioc.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    Map<String,Object> beanMap = new HashMap<String, Object>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitions){
        for (BeanDefinition beanDefinition : beanDefinitions){
            String beanClassName = beanDefinition.getBeanClassName();
            Object object = initClassObject(beanClassName);
            beanMap.put(beanDefinition.getBeanId(),object);
        }
    }

    private Object initClassObject(String beanClassName){
        try {
            Class<?> clazz = Class.forName(beanClassName);
            Object object = clazz.newInstance();
            return object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Object getBean(String beanId){
        return beanMap.get(beanId);
    }

    @SuppressWarnings("uncheck")
    public <T> T getBean(String beanId,Class<T> clazz){
        return (T) beanMap.get(beanId);
    }
}
