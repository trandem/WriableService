package com.example.user.handler;

import com.example.user.repo.UserService;
import com.example.user.request.UserRequest;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
@Data
public class HandlerManager {

    @Autowired
    private UserService service;

    @Value("${num.handle:3}")
    private int numHandle;

    private UserHandle[] userHandles;


    @PostConstruct
    public void creatUserHandle() {
        userHandles = new UserHandle[numHandle];
        for (int i = 0; i < numHandle; i++) {
            UserHandle userHandle = new UserHandle(service);
            userHandles[i] = userHandle;
            Thread thread = new Thread(userHandle);
            thread.setName("process data " + i);
            thread.start();
        }
    }

    public XFuture<?> addToHandle(UserRequest request) {
        int userId = request.getUserId();
        XFuture<?> xFuture = new XFuture();
        CacheManager.future.put(request.getId(), xFuture);
        userHandles[userId % numHandle].getQueue().offer(request);
        return xFuture;
    }

}
