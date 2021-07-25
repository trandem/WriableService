package com.example.user.response.impl;

import com.example.user.response.UserResponse;
import com.example.user.model.User;
import lombok.Data;

@Data
public class QueryResponse extends UserResponse {
    protected User user;
}
