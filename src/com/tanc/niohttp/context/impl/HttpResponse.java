package com.tanc.niohttp.context.impl;


import com.tanc.niohttp.context.Response;

import java.nio.channels.SelectionKey;

public class HttpResponse implements Response {

    private SelectionKey key;

    private String contentType = "text/html";

    private int statuCode = 200;
    private String statuCodeStr = "OK";
    private String htmlFile = "";

    public HttpResponse(SelectionKey key){
        this.key = key;
    }


    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public int getStatuCode() {
        return statuCode;
    }

    @Override
    public String getStatuCodeStr() {
        return statuCodeStr;
    }

    @Override
    public String getHtmlFile() {
        return htmlFile;
    }

    @Override
    public void setHtmlFile(String htmlFile) {
        this.htmlFile = htmlFile;
    }

    @Override
    public SelectionKey getKey() {
        return key;
    }

    @Override
    public void setContentType(String contentTypr) {
        this.contentType = contentTypr;
    }

    @Override
    public void setStatuCode(int statuCode) {
        this.statuCode = statuCode;
    }

    @Override
    public void setStatuCodeStr(String statuCodeStr) {
        this.statuCodeStr = statuCodeStr;
    }
}
