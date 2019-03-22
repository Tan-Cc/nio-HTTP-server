package com.tanc.niohttp.handler;

import com.tanc.niohttp.utils.XMLUtils;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapHandler {

    private static Map<String, Handler> handleMap = new HashMap<>();
    private static volatile MapHandler instance = null;

    private MapHandler(){}

    public static MapHandler getInstance(){
        if(instance == null){
            synchronized (MapHandler.class){
                if (instance == null){

                    instance = new MapHandler();
                    String urlpatten = "/login";
                    String classpath = "com.tanc.niohttp.handler.LoginHandler";

                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(classpath);
                        Handler handler = (Handler) clazz.newInstance();
                        instance.getHandleMap().put(urlpatten,handler);
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }


/*                    instance = new MapHandler();
                    Element rootElement = XMLUtils.getRootElement("resource/web.xml");
                    List<Element> handlers = XMLUtils.getElements(rootElement);

                    for(Element element : handlers){
                        Element urlP = XMLUtils.getElement(element,"url-patten");
                        String urlpatten = XMLUtils.getElementText(urlP);

                        Element handleClassE = XMLUtils.getElement(element,"handler-class");
                        String classpath = XMLUtils.getElementText(handleClassE);

                        Class<?> clazz = null;
                        try {
                            clazz = Class.forName(classpath);
                            Handler handler = (Handler) clazz.newInstance();
                            instance.getHandleMap().put(urlpatten,handler);
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }*/
                }
            }
        }
        return instance;
    }

    public Map<String, Handler> getHandleMap(){
        return handleMap;
    }
}
