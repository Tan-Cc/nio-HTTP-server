package com.tanc.niohttp.utils;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

public class XMLUtils {

    private static Logger logger = Logger.getLogger(XMLUtils.class);
    private static SAXReader reader = new SAXReader();

    public static Element getRootElement(String xmlPath) {
        Document document = null;
        try {
            document = reader.read(new File(xmlPath));
        }catch (Exception e){
            logger.error("找不到指定路径的XML文件" + xmlPath);
            return null;
        }
        return document.getRootElement();
    }

    @SuppressWarnings("unchecked")
    public static List<Element> getElements(Element element){
        return element.elements();
    }

    public static Element getElement(Element element, String name) {
        Element cElement = element.element(name);
        if(cElement == null){
            logger.error(element.getName() + "节点下没有子节点" + name);
            return null;
        }
        return cElement;
    }

    public static String getElementText(Element element) {
        return element.getText();
    }
}
