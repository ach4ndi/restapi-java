package com.andi.shopapi.shopapi.model.request;

import lombok.Data;

@Data
public class UserCreationRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private Boolean isRegistered;
}
