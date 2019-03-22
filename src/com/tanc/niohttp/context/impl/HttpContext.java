package com.tanc.niohttp.context.impl;

import com.tanc.niohttp.context.Context;
import com.tanc.niohttp.context.Request;
import com.tanc.niohttp.context.Response;

import java.nio.channels.SelectionKey;

public class HttpContext extends Context {

    private Request request;
    private Response response;


    @Override
    public void setContext(String requestHeader, SelectionKey key) {
        request = new HttpRequest(requestHeader);
        response = new HttpResponse(key);

        setRequest();
        setResponse();
    }

    private void setRequest() {
        super.request = this.request;
    }

    private void setResponse() {
        super.response = this.response;
    }
}
