package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;
import org.apache.log4j.Logger;

public class LoginHandler extends AbsHandler{

    private Logger logger = Logger.getLogger(LoginHandler.class);

    @Override
    public void doGet(Context context){
        logger.info("进入登录页面");
        context.getResponse().setHtmlFile("F:\\javaProject\\nioHttp\\nioHttp\\login.html");
    }
}
