package com.heng.ioc.test;


import com.heng.ioc.bean.Hand;
import com.heng.ioc.bean.People;
import com.heng.ioc.factory.IOCFactory;
import com.heng.ioc.factory.config.XMLIocFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {

    public static void main(String[] args) {
        IOCFactory iocFactory = new XMLIocFactory("ioc.xml");
        People people = iocFactory.getBean("people",People.class);
        Hand hand = iocFactory.getBean("hand",Hand.class);
        System.out.println("");
    }
}
