package com.heng.ioc.config.parser;

import com.heng.ioc.factory.config.BeanDefinition;
import com.heng.ioc.factory.config.BeanDefinitionImpl;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultElement;
import org.dom4j.tree.DefaultText;

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
                    //设置BeanDefinition的构造参数
                    setBeanConstruct(beanDefinition,element);
                    //设置BeanDefinition的成员变量
                    setBeanAttribute(beanDefinition,element);
                    list.add(beanDefinition);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取bean的类名与id
     * @param beanDefinition
     * @param element
     */
    private void setBeanClassName(BeanDefinition beanDefinition,Element element){
        String beanID = element.attributeValue("id");
        String beanClassName = element.attributeValue("class");
        beanDefinition.setBeanId(beanID);
        beanDefinition.setBeanClassName(beanClassName);
    }

    /**
     * 获取bean的构造参数
     * @param beanDefinition
     * @param element
     */
    private void setBeanConstruct(BeanDefinition beanDefinition,Element element) throws Exception {
        for (Iterator<Element> it = element.elementIterator();it.hasNext();){
            Element el = it.next();
            if (el.getQName().getName().equals("constructor-arg")){
                if (el.attributeValue("index") == null){
                    throw new IllegalArgumentException("construct-arg must specify value,for example:" + "   <constructor-arg index=\"0\">");
                }

                Integer index = Integer.valueOf(el.attributeValue("index"));   //手动指定的index

                if (index < 0){
                    throw new Exception("construct-arg index can't less than 0");
                }

                for (Iterator<Element> vals = el.elementIterator();vals.hasNext();){
                     Element val = vals.next();
                     if (val.getQName().getName().equals("value")){
                         beanDefinition.setConstructArg(index,val.getText().trim());
                     }
                 }

            }
        }
    }

    /**
     * 获取bean的属性
     * @param beanDefinition
     * @param element
     */
    private void setBeanAttribute(BeanDefinition beanDefinition,Element element){
        for (Iterator<Element> it = element.elementIterator();it.hasNext();){
            Element el = it.next();
            if (el.getQName().getName().equals("property")){
                beanDefinition.setAttribute(el.attributeValue("name"),el.attributeValue("value"));
            }
        }
    }
}
