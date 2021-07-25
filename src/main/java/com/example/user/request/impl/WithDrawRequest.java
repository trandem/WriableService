package com.example.user.request.impl;

import com.example.user.request.UserRequest;
import lombok.Data;

import static com.example.user.handler.Action.BaseAction.WITHDRAW;

@Data
public class WithDrawRequest extends UserRequest {
    private int amount;
    public WithDrawRequest() {
        super();
        this.type = WITHDRAW;
    }

}
