package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;
import com.tanc.niohttp.context.Response;
import org.apache.log4j.Logger;

public class NotFoundHandler extends AbsHandler{

    private Response response;
    private Logger logger = Logger.getLogger(NotFoundHandler.class);

    @Override
    public void doGet(Context context){

        logger.info("404");
        response = context.getResponse();

        response.setStatuCode(404);
        response.setStatuCodeStr("Not Found");
        response.setHtmlFile("F:\\javaProject\\nioHttp\\nioHttp\\404.html");

    }
}
