package com.heng.ioc.factory;

import com.heng.ioc.factory.config.BeanDefinition;
import com.heng.ioc.utils.ClassUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    private List<BeanDefinition> beanDefinitions;

    Map<String,Object> beanMap = new HashMap<>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitions){
        this.beanDefinitions = beanDefinitions;
        for (BeanDefinition beanDefinition : beanDefinitions){
            Object object = beanMap.get(beanDefinition.getBeanId()) == null ? initClassObject(beanDefinition) : beanMap.get(beanDefinition.getBeanId());
            initObjectAttribute(object,beanDefinition);
            beanMap.put(beanDefinition.getBeanId(),object);
        }
    }

    /**
     * 通过构造函数初始化对象，需要注意的是，该对象的属性是没有被初始化的。
     * @param beanDefinition
     * @return
     */
    private Object initClassObject(BeanDefinition beanDefinition){
        try {
            Class<?> clazz = Class.forName(beanDefinition.getBeanClassName());
            Constructor<?>[] constructors = clazz.getConstructors();
            if (constructors.length == 1){
                return constructors[0].newInstance();
            }
            for (Constructor constructor : constructors){
                if (constructor.getParameterCount() == beanDefinition.getConstructArgCounts()){
                    Object[] args = new Object[beanDefinition.getConstructArgCounts()];
                    for (int i = 0;i < args.length;i++){

                        args[i] = ClassUtils.typeConvert(beanDefinition.getConstructArg(i).toString(),constructor.getParameterTypes()[i]);
                    }
                    return constructor.newInstance(args);
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initClassObjectByBeanId(String beanId){
        for (BeanDefinition beanDefinition : beanDefinitions){
            if (beanId.equals(beanDefinition.getBeanId()) && beanMap.get(beanDefinition.getBeanId()) == null){
                Object object = this.initClassObject(beanDefinition);
                beanMap.put(beanDefinition.getBeanId(),object);
            }
        }
    }

    private Object initObjectAttribute(Object object,BeanDefinition beanDefinition){
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){

            field.setAccessible(true);
            if (beanDefinition.getAttribute(field.getName()) != null){
                try {
                    //该字段是否是基本类型
                    if (field.getType().isPrimitive() || ClassUtils.isPrimitive(field.getType())){
                        field.set(object, ClassUtils.typeConvert(beanDefinition.getAttribute(field.getName()).toString(),field.getType()));
                    }else {
                        Object attribute = this.getBean(field.getName());
                        if (attribute == null){
                            initClassObjectByBeanId(field.getName());
                            attribute = this.getBean(field.getName());
                        }
                        field.set(object,attribute);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
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
