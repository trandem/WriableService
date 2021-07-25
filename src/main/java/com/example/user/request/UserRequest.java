package com.example.user.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public abstract class UserRequest {
    protected Long id;
    protected Integer userId;
    protected String type;


}
