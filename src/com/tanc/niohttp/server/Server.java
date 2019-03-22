package com.tanc.niohttp.server;


import com.tanc.niohttp.handler.HttpHandler;
import com.tanc.niohttp.utils.XMLUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Server implements Runnable{

    private boolean interrupted = false;

    private Logger logger = Logger.getLogger(Server.class);

    public Server(boolean inter){
        this.interrupted = inter;
    }

    @Override
    public void run() {
        try {
            //开启selector
            Selector selector = Selector.open();
            //实例化通道,并绑定端口
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //String strport = XMLUtils.getRootElement("server.xml").element("port").getText();
            //serverSocketChannel.socket().bind(new InetSocketAddress(Integer.parseInt(strport)));

            serverSocketChannel.socket().bind(new InetSocketAddress(8089));

            logger.info("成功绑定端口8089");

            //设置为通道非阻塞
            serverSocketChannel.configureBlocking(false);
            //将通道注册到selector中
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            logger.info("服务器启动成功，开始监听");

            while(!interrupted){
                int readyChannels = selector.select();
                if (readyChannels == 0) continue;

                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();
                while (iterator.hasNext()){
                    SelectionKey key = iterator.next();

                    if(key.isAcceptable()){

                        SocketChannel socketChannel = serverSocketChannel.accept();
                        if(socketChannel != null){
                            logger.info("收到来自" + ((InetSocketAddress)socketChannel.getRemoteAddress()).getHostName() + "的信号");
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                        }

                    }else if(key.isReadable()){

                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        String requestHeader = "";

                        //拿到通道中的HTTP请求头
                        try{
                            requestHeader = receive(socketChannel);
                        }catch (Exception e){
                            logger.error("读取channel失败");
                            return;
                        }

                        if(requestHeader.length() > 0){
                            logger.info("收到请求头" + requestHeader);
                            logger.info("开启子进程...");
                            new Thread(new HttpHandler(requestHeader,key)).start();
                        }

                    }else if(key.isWritable()){
                        logger.info("有流写出！");
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        socketChannel.shutdownInput();
                        socketChannel.close();
                    }
                    iterator.remove();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String receive(SocketChannel socketChannel) throws Exception {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] bytes = null;
        int size = 0;

        ByteArrayOutputStream bout = new ByteArrayOutputStream();

        while((size = socketChannel.read(buffer)) > 0){
            //通道中的内容写到buffer后，转换为读模式
            buffer.flip();
            bytes = new byte[size];
            buffer.get(bytes);
            bout.write(bytes);

            buffer.clear();
        }

        bytes = bout.toByteArray();
        return new String(bytes,"UTF-8");
    }
}
