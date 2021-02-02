package com.heng.ioc.test;

import com.heng.ioc.bean.People;
import com.heng.ioc.config.parser.BeanConfigParser;
import com.heng.ioc.factory.IOCFactory;
import com.heng.ioc.factory.config.BeanDefinition;
import com.heng.ioc.factory.config.XMLIocFactory;

import java.util.List;

public class Test {

    public static void main(String[] args) {
        IOCFactory iocFactory = new XMLIocFactory("ioc.xml");
        People people = iocFactory.getBean("people",People.class);
        System.out.println("");
    }
}
