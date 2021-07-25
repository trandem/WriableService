package com.example.user.handler.Action.impl;

import com.example.user.handler.Action.BaseAction;
import com.example.user.handler.CacheManager;
import com.example.user.model.User;
import com.example.user.repo.UserService;
import com.example.user.request.impl.WithDrawRequest;
import com.example.user.response.impl.WithDrawResponse;

import java.util.concurrent.locks.ReentrantReadWriteLock;


public class WithDrawAction extends BaseAction<WithDrawRequest, WithDrawResponse, UserService> {

    public WithDrawAction(UserService service) {
        super(service);
    }

    @Override
    public WithDrawResponse doAction(WithDrawRequest withDrawRequest) {
        WithDrawResponse response = new WithDrawResponse();
        response.setRequestId(withDrawRequest.getId());
        User user;
        if (CacheManager.user.containsKey(withDrawRequest.getUserId())) {
            user = CacheManager.user.get(withDrawRequest.getUserId());
        } else {
            user = service.findUserById(withDrawRequest.getUserId());
        }

        if (user == null) {
            response.setMsg("null");
            return response;
        }

        if (user.getTotalMoney() < withDrawRequest.getAmount()) {
            response.setMsg("not enough");
            return response;
        } else {
            user.setTotalMoney(user.getTotalMoney() - withDrawRequest.getAmount());
            try {
                user = service.updateUser(user);
                CacheManager.user.put(user.getId(), user);
                response.setMsg("ok");
            } catch (IllegalStateException exception) {
                exception.printStackTrace();
                response.setMsg("version is not ok");
            }
        }

        return response;
    }

}
