package com.example.user.handler;

import com.example.user.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class CacheManager {
    public static final Map<Integer, User> user = new ConcurrentHashMap<>();

    public static final Map<Long, XFuture> future = new ConcurrentHashMap<>();

}
