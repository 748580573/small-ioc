package com.heng.ioc.factory.config;

import java.util.*;

public class BeanDefinitionImpl implements BeanDefinition{

    private String beanClassName;

    private String beanId;

    private Map<Integer,Object> constructArgsMap;

    private Map<String,Object> attributesMap;

    private List<String> depends;

    public BeanDefinitionImpl(){
        this.attributesMap = new HashMap<String, Object>();
        this.constructArgsMap = new HashMap<Integer, Object>();
        this.depends = new ArrayList<String>();
    }

    public void setConstructArg(int index, Object object) {
        constructArgsMap.put(index,object);
    }

    public Object getConstructArg(int index) {
        return constructArgsMap.get(index);
    }

    @Override
    public int getConstructArgCounts() {
        return constructArgsMap.keySet().size();
    }

    public void setAttribute(String name, Object value) {
        this.attributesMap.put(name,value);
    }

    public Object getAttribute(String name) {
        return this.attributesMap.get(name);
    }

    public void setBeanId(String beanId) {
        this.beanId = beanId;
    }

    public String getBeanId() {
        return this.beanId;
    }

    public void setBeanClassName(String beanClassName) {
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }

    public void setDependsOn(String... dependsOn) {
        this.depends.addAll(Arrays.asList(dependsOn));
    }

    public List<String> getDependsOn() {
        return this.depends;
    }

    public void setAutowireCandidate(boolean autowireCandidate) {

    }

    public boolean isAutowireCandidate() {
        return false;
    }
}
