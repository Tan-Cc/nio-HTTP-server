package com.tanc.niohttp.server;

public class App {
    public static void main(String[] args) {
        new Thread(new Server(false)).start();
    }
}
