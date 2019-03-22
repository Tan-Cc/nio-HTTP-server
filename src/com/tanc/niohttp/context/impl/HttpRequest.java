package com.tanc.niohttp.context.impl;

import com.tanc.niohttp.context.Request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HttpRequest implements Request {

    private Map<String, Object> attribute = new HashMap<>();
    private Map<String, Object> headers = new HashMap<>();

    private String method;
    private String uri;
    private String protocol;

    public HttpRequest(String httpHeader) {
        init(httpHeader);

    }

    private void init(String httpHeader) {
        String[] headers = httpHeader.split("\r\n");  //windows下回车是\r\n，Linux下是\n
        //GET-------POST
        initMethod(headers[0]);
        //URI
        initURI(headers[0]);
        //HTTP版本协议
        initProtocol(headers[0]);
        //除第一行外请求头的其他信息
        initRequestHeaders(headers);
    }

    private void initMethod(String str) {
        method = str.substring(0,str.indexOf(" "));
    }

    private void initAttribute(String attr) {
        String[] attrs =attr.split("&");
        for(String string : attrs){
            String key = string.substring(0,string.indexOf("="));
            String value = string.substring(string.indexOf("=")+1);
            attribute.put(key,value);
        }
    }

    private void initURI(String str) {
        uri = str.substring(str.indexOf(" ")+1, str.indexOf(" ", str.indexOf(" ")+1));

        if(method.toUpperCase().equals("GET")) {
            if(uri.contains("?")){
                String attr = uri.substring(uri.indexOf("?")+1);
                uri = uri.substring(0, uri.indexOf("?"));
                initAttribute(attr);
            }
        }
    }

    private void initRequestHeaders(String[] strs){
        for (int i = 1; i < strs.length; i++) {
            String key = strs[i].substring(0,strs[i].indexOf(":"));
            String value = strs[i].substring(strs[i].indexOf(":")+1);
            headers.put(key,value);
        }
    }

    private void initProtocol(String str){
        protocol = str.substring(str.lastIndexOf(" ")+1);
    }


    @Override
    public Map<String, Object> getAttribute() {
        return attribute;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getUri() {
        return uri;
    }

    @Override
    public String getProtocol() {
        return protocol;
    }

    @Override
    public Map<String, Object> getHeaders() {
        return headers;
    }

    @Override
    public Set<String> getHeaderNames() {
        return headers.keySet();
    }

    @Override
    public Object getHeader(String key) {
        return headers.get(key);
    }
}
