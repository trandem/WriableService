package com.example.user.request.impl;

import com.example.user.request.UserRequest;
import lombok.Data;

import static com.example.user.handler.Action.BaseAction.QUERY_TYPE;

@Data
public class QueryUserRequest extends UserRequest {

    public QueryUserRequest() {
        super();
        this.type = QUERY_TYPE;
    }

    public QueryUserRequest(Long id, Integer userId, String type) {
        super(id, userId, type);
    }
}
