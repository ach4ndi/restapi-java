package com.andi.shopapi.shopapi.repository;

import com.andi.shopapi.shopapi.model.Order;
import com.andi.shopapi.shopapi.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByUserId(User userId);
    List<Order> findByOrderId(String orderId);
}
