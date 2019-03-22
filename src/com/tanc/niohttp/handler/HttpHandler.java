package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;
import com.tanc.niohttp.context.impl.HttpContext;

import java.nio.channels.SelectionKey;

public class HttpHandler implements Runnable {

    private String requestHeader;
    private SelectionKey key;
    private Context context = new HttpContext();

    private Handler handler;

    public HttpHandler(String hq, SelectionKey key){
        this.requestHeader = hq;
        this.key = key;
    }
    @Override
    public void run() {
        context.setContext(requestHeader,key);
        String uri = context.getRequest().getUri();

        handler = MapHandler.getInstance().getHandleMap().get(uri);

        if(handler == null){
            handler = new NotFoundHandler();
        }

        handler.init(context);
    }
}
