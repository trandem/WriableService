package com.example.user.handler.Action;

import com.example.user.request.UserRequest;
import com.example.user.response.UserResponse;
import com.example.user.repo.UserService;

public abstract class BaseAction<Req extends UserRequest, Res extends UserResponse,Ser extends UserService> {
    public static final String QUERY_TYPE ="query";
    public static final String WITHDRAW ="withdraw";
    protected final Ser service;

    public BaseAction(Ser service) {
        this.service = service;
    }

    public abstract Res doAction(Req req);
}
