package com.andi.shopapi.shopapi.model.result;

import java.util.ArrayList;
import java.util.List;

import com.andi.shopapi.shopapi.model.Order;
import com.andi.shopapi.shopapi.model.OrderUser;

public class OrderResult {
    public OrderUser orderUser;
    public List<Order> orders = new ArrayList<>();
}
