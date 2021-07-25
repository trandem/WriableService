package com.example.user.response.impl;

import com.example.user.model.User;
import com.example.user.response.UserResponse;
import lombok.Data;

@Data
public class WithDrawResponse extends UserResponse {
    protected User user;
}
