package com.andi.shopapi.shopapi.repository;

import com.andi.shopapi.shopapi.model.User;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findByEmail(String email);
}
