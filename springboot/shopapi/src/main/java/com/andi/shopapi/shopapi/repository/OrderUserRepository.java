package com.andi.shopapi.shopapi.repository;

import com.andi.shopapi.shopapi.model.OrderUser;
import com.andi.shopapi.shopapi.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderUserRepository extends MongoRepository<OrderUser, String> {
    List<OrderUser> findByUserId(User userId);
}
