package com.heng.ioc.config.parser;

import com.heng.ioc.factory.config.BeanDefinition;
import com.heng.ioc.factory.config.BeanDefinitionImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeanConfigParser {

    public List<BeanDefinition> parser(String path){
        SAXReader reader = new SAXReader();
        reader.setIgnoreComments(true);
        List<BeanDefinition> list = new ArrayList<BeanDefinition>();
        InputStream in = null;
        try {
            in = this.getClass().getResourceAsStream("/" + path);
            Document read = reader.read(in);
            Element root = read.getRootElement();
            for (Iterator<Element> it = root.elementIterator(); it.hasNext(); ){
                Element element = it.next();
                if (element.getQName().getName().equals("bean")){
                    BeanDefinition beanDefinition = new BeanDefinitionImpl();
                    //设置BeanDefinition的名字
                    setBeanClassName(beanDefinition,element);
                    //设置BeanDefinition的成员变量
                    setBeanAttribute(beanDefinition,element);
                    list.add(beanDefinition);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void setBeanClassName(BeanDefinition beanDefinition,Element element){
        String beanID = element.attributeValue("id");
        String beanClassName = element.attributeValue("class");
        beanDefinition.setBeanId(beanID);
        beanDefinition.setBeanClassName(beanClassName);
    }

    private void setBeanAttribute(BeanDefinition beanAttribute,Element element){
        for (Iterator<Element> it = element.elementIterator();it.hasNext();){
            Element el = it.next();
            if (el.getQName().getName().equals("property")){
                beanAttribute.setAttribute(el.attributeValue("name"),el.attributeValue("value"));
            }
        }
    }
}
