package com.andi.shopapi.shopapi.model.response;

import java.util.ArrayList;
import java.util.List;

import com.andi.shopapi.shopapi.model.Order;
import com.andi.shopapi.shopapi.model.OrderUser;

public class OrderResponse extends DefaultResponse {
    public OrderUser orderData;
    public List<Order> entries = new ArrayList<>();
}
