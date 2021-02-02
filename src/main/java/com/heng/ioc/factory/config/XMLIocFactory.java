package com.heng.ioc.factory.config;

import com.heng.ioc.config.parser.BeanConfigParser;
import com.heng.ioc.factory.BeanFactory;
import com.heng.ioc.factory.IOCFactory;

import java.util.List;

public class XMLIocFactory implements IOCFactory {

    private BeanFactory beanFactory;

    private BeanConfigParser configParser;

    public XMLIocFactory(String path){
        beanFactory = new BeanFactory();
        configParser = new BeanConfigParser();
        loadBeanDefinitions(path);
    }

    private void loadBeanDefinitions(String configPath){
        List<BeanDefinition> beanDefinitions = configParser.parser(configPath);
        beanFactory.addBeanDefinitions(beanDefinitions);
    }

    public Object getBean(String beanId) {
        return beanFactory.getBean(beanId);
    }

    public <T> T getBean(String beanId, Class<T> clazz) {
        return beanFactory.getBean(beanId,clazz);
    }

    public <T> T getBean(T clazz) {
        return null;
    }
}
