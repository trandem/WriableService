package com.example.user.handler.Action.impl;

import com.example.user.handler.Action.BaseAction;
import com.example.user.handler.CacheManager;
import com.example.user.model.User;
import com.example.user.request.impl.QueryUserRequest;
import com.example.user.response.impl.QueryResponse;
import com.example.user.repo.UserService;

public class QueryAction extends BaseAction<QueryUserRequest, QueryResponse, UserService> {

    public QueryAction(UserService service) {
        super(service);
    }

    @Override
    public QueryResponse doAction(QueryUserRequest queryUserRequest) {
        QueryResponse response = new QueryResponse();
        response.setRequestId(queryUserRequest.getId());
        if (CacheManager.user.containsKey(queryUserRequest.getUserId())) {
            response.setUser(CacheManager.user.get(queryUserRequest.getUserId()));
            return response;
        }
        User user = service.findUserById(queryUserRequest.getUserId());
        response.setUser(user);

        System.out.println("query db " + user + " thread " + Thread.currentThread().getName());
        if (user == null) {
            response.setMsg("not Found");
        } else {
            response.setMsg("ok");
            CacheManager.user.put(user.getId(), user);
        }
        return response;
    }

}
