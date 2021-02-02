package com.heng.ioc.factory;

import com.heng.ioc.factory.config.BeanDefinition;
import com.heng.ioc.utils.TypeConvertUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {

    Map<String,Object> beanMap = new HashMap<String, Object>();

    public void addBeanDefinitions(List<BeanDefinition> beanDefinitions){
        for (BeanDefinition beanDefinition : beanDefinitions){
            Object object = initClassObject(beanDefinition);
            initObjectAttribute(object,beanDefinition);
            beanMap.put(beanDefinition.getBeanId(),object);
        }
    }

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

                        args[i] = TypeConvertUtils.typeConvert(beanDefinition.getConstructArg(i).toString(),constructor.getParameterTypes()[i]);
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

    private Object initObjectAttribute(Object object,BeanDefinition beanDefinition){
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            field.setAccessible(true);
            if (beanDefinition.getAttribute(field.getName()) != null){
                try {
                    field.set(object, TypeConvertUtils.typeConvert(beanDefinition.getAttribute(field.getName()).toString(),field.getType()));
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
