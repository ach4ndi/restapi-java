package com.andi.shopapi.shopapi.model.response;

import java.util.ArrayList;
import java.util.List;

import com.andi.shopapi.shopapi.model.User;

public class UserResponse extends DefaultResponse {
    public List<User> data = new ArrayList<>();
}
