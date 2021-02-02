package com.heng.ioc.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;

/**
 * XML文件解析器
 */
public class XMLParserUtils {


    public static void main(String[] args) throws Exception {
        SAXReader reader = new SAXReader();
        reader.setIgnoreComments(true);

        Document read = reader.read(new File("E:\\idea_file\\small-ioc\\src\\main\\resources\\ioc.xml"));
        Element root = read.getRootElement();
        for (Iterator<Element> it = root.elementIterator();it.hasNext(); ){
            Element element = it.next();
            System.out.println("hello");
        }
    }
}
