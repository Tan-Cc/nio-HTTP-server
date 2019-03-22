package com.tanc.niohttp.context;


import com.tanc.niohttp.utils.XMLUtils;

import java.nio.channels.SelectionKey;

public interface Response {


    //String SERVER_NAME = XMLUtils.getRootElement("resource/server.xml").element("serverName").getText();
    String SERVER_NAME = "tanCc Server";

    String getContentType();

    int getStatuCode();

    String getStatuCodeStr();

    String getHtmlFile();

    void setHtmlFile(String htmlFile);

    SelectionKey getKey();

    void setContentType(String contentTypr);

    void setStatuCode(int statuCode);

    void setStatuCodeStr(String statuCodeStr);
}
