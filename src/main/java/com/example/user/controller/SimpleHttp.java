package com.example.user.controller;

import com.example.user.handler.CacheManager;
import com.example.user.handler.HandlerManager;
import com.example.user.handler.IdGenerator;
import com.example.user.handler.XFuture;
import com.example.user.model.User;
import com.example.user.repo.UserService;
import com.example.user.request.impl.QueryUserRequest;
import com.example.user.request.impl.WithDrawRequest;
import com.example.user.response.impl.QueryResponse;
import com.example.user.response.impl.WithDrawResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
public class SimpleHttp {

    @Autowired
    HandlerManager manager;

    @Autowired
    IdGenerator generator;

    @Autowired
    UserService service;

    @RequestMapping("/getUser")
    User getUser(@RequestParam int userId) throws InterruptedException, TimeoutException {
        if (CacheManager.user.containsKey(userId)) {
            return CacheManager.user.get(userId);
        }
        QueryUserRequest queryUserRequest = new QueryUserRequest();
        queryUserRequest.setId(generator.nextId());
        queryUserRequest.setUserId(userId);

        XFuture<QueryResponse> future = (XFuture<QueryResponse>) manager.addToHandle(queryUserRequest);
        return future.get(200, TimeUnit.MILLISECONDS).getUser();
    }

    @RequestMapping("/with-draw")
    WithDrawResponse withDraw(@RequestParam int userId, @RequestParam int amount) throws TimeoutException, InterruptedException {
        WithDrawRequest request = new WithDrawRequest();
        request.setAmount(amount);
        request.setUserId(userId);
        request.setId(generator.nextId());
        XFuture<WithDrawResponse> future = (XFuture<WithDrawResponse>) manager.addToHandle(request);
        return future.get(200, TimeUnit.MILLISECONDS);
    }

    @RequestMapping("/get-user-normal")
    User getUserNormal(@RequestParam int userId) throws InterruptedException {
        if (CacheManager.user.containsKey(userId)) {
            return CacheManager.user.get(userId);
        }
        System.out.println("normal query " + Thread.currentThread());
        User user = service.findUserById(userId);
        if (user != null) {
            CacheManager.user.put(user.getId(), user);
        }
        return user;
    }
    @GetMapping("/test")
    public String test() {
        return "successful";
    }

    @GetMapping(value = "/gophertiles_files/{name}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable String name) throws IOException, InterruptedException {
        Thread.sleep(100);
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                "image/" + name
        )));
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);

    }

}
