package com.tanc.niohttp.context;

import java.util.Map;
import java.util.Set;

public interface Request {

    String POST = "POST";
    String GET = "GET";

    Map<String, Object> getAttribute();

    String getMethod();

    String getUri();

    String getProtocol();

    Map<String, Object> getHeaders();

    Set<String> getHeaderNames();

    Object getHeader(String key);
}
