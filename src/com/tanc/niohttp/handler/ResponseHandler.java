package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;
import com.tanc.niohttp.context.Request;
import com.tanc.niohttp.context.Response;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Date;

//封装response响应
public class ResponseHandler {

    private Request request;
    private Response response;
    private String protocol;
    private int statuCode;
    private String statuCodeStr;
    private ByteBuffer buffer;
    private String serverName;
    private String contentType;
    private SocketChannel channel;
    private Selector selector;
    private SelectionKey key;
    private Logger logger = Logger.getLogger(ResponseHandler.class);
    private BufferedReader reader;
    private String htmlFile;

    public void write(Context context) {

        request = context.getRequest();
        response = context.getResponse();
        buffer = ByteBuffer.allocate(1024);
        protocol = request.getProtocol();
        statuCode = response.getStatuCode();
        statuCodeStr = response.getStatuCodeStr();
        serverName = Response.SERVER_NAME;
        contentType = response.getContentType();
        key = response.getKey();
        selector = key.selector();
        channel = (SocketChannel) key.channel();
        htmlFile = response.getHtmlFile();

        String html = setHtml(context);

        StringBuilder sb = new StringBuilder();

        sb.append(protocol + " " + statuCode + " " + statuCodeStr + "\r\n");

        sb.append("Server: " + serverName + "\r\n");
        sb.append("Content-Type: " + contentType + "\r\n");
        sb.append("Date: " + new Date() + "\r\n");
        if(reader != null) {
            sb.append("Content-Length: " + html.getBytes().length + "\r\n");
        }

        //响应内容
        sb.append("\r\n");
        sb.append(html);

        buffer.put(sb.toString().getBytes());
        //从写模式，切换到读模式
        buffer.flip();
        try {
            logger.info("生成相应\r\n" + sb.toString());
            channel.register(selector, SelectionKey.OP_WRITE);
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String setHtml(Context context) {
        StringBuilder html = null;
        if(htmlFile != null && htmlFile.length() > 0) {
            html = new StringBuilder();

            try {
                reader = new BufferedReader(new FileReader(new File(htmlFile)));
                String htmlstr;
                htmlstr = reader.readLine();
                while (htmlstr != null){
                    html.append(htmlstr + "\r\n");
                    htmlstr = reader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(html == null) {
            System.out.println("================html读取不到");
            return null;
        }
        return html.toString();
    }
}
