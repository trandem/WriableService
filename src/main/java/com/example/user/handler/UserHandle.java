package com.example.user.handler;

import com.example.user.handler.Action.BaseAction;
import com.example.user.handler.Action.impl.QueryAction;
import com.example.user.handler.Action.impl.WithDrawAction;
import com.example.user.response.UserResponse;
import com.example.user.request.UserRequest;
import com.example.user.repo.UserService;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static com.example.user.handler.Action.BaseAction.QUERY_TYPE;
import static com.example.user.handler.Action.BaseAction.WITHDRAW;

@Data
public class UserHandle implements Runnable {

    private final UserService userService;

    private final BlockingQueue<UserRequest> queue = new LinkedBlockingQueue<>(2000);

    private final Map<String, BaseAction> action;

    private boolean isStop;

    public UserHandle(UserService repo) {
        this.userService = repo;
        this.action = new HashMap<>();
        this.action.put(QUERY_TYPE, new QueryAction(userService));
        this.action.put(WITHDRAW, new WithDrawAction(userService));
        this.isStop = false;
    }

    @Override
    public void run() {
        while (!isStop) {
            UserRequest request = queue.poll();
            if (request == null) {
                continue;
            }
            if (action.containsKey(request.getType())) {
                UserResponse response = this.action.get(request.getType()).doAction(request);

                XFuture<UserResponse> responseFuture = CacheManager.future.remove(response.getRequestId());

                if (responseFuture != null) {
                    responseFuture.setResult(response);
                }
            }
        }
    }

    public void stop() {
        isStop = true;
    }
}
