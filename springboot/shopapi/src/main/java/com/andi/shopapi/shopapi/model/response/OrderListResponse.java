package com.andi.shopapi.shopapi.model.response;

import java.util.ArrayList;
import java.util.List;

import com.andi.shopapi.shopapi.model.OrderUser;

public class OrderListResponse extends DefaultResponse {
    public List<OrderUser> data = new ArrayList<>();
}
