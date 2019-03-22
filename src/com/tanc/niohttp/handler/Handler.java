package com.tanc.niohttp.handler;

import com.tanc.niohttp.context.Context;

public interface Handler {

    void init(Context context);

    void service(Context context);

    void doGet(Context context);

    void doPost(Context context);

    void destory(Context context);
}
