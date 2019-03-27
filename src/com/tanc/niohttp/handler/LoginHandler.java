package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;
import org.apache.log4j.Logger;

import java.util.Map;

public class LoginHandler extends AbsHandler{

    private Logger logger = Logger.getLogger(LoginHandler.class);

    @Override
    public void doGet(Context context){
        logger.info("进入登录页面");
        context.getResponse().setHtmlFile("F:\\javaProject\\nioHttp\\nioHttp\\login.html");
    }

    @Override
    public void doPost(Context context){
        logger.info("输入了用户名密码");
        Map<String, Object> attribute = context.getRequest().getAttribute();
        if(attribute.get("userName").equals("tanc") && attribute.get("password").equals("123")){
            logger.info("登录成功");
            context.getResponse().setHtmlFile("F:\\javaProject\\nioHttp\\nioHttp\\loginsuccess.html");
        }else {
            logger.info("用户名密码错误");
        }
    }
}
