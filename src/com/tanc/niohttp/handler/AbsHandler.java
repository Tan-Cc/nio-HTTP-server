package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;
import com.tanc.niohttp.context.Request;

public class AbsHandler implements Handler {

    protected Context context;
    @Override
    public void init(Context context) {
        this.context = context;
        this.service(context);
    }

    @Override
    public void service(Context context) {
        String method = context.getRequest().getMethod();
        if(method.equals(Request.GET)){
            this.doGet(context);
        }else if(method.equals(Request.POST)){
            this.doPost(context);
        }
        sendResponse(context);
    }

    @Override
    public void doGet(Context context) {

    }

    @Override
    public void doPost(Context context) {

    }

    @Override
    public void destory(Context context) {

    }

    private void sendResponse(Context context){
        new ResponseHandler().write(context);
    }
}
