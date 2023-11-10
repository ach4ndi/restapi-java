package com.andi.shopapi.shopapi.repository;

import com.andi.shopapi.shopapi.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
