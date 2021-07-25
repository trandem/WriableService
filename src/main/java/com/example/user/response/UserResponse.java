package com.example.user.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class UserResponse {
    protected long requestId;
    protected String type;
    protected String msg;
}
