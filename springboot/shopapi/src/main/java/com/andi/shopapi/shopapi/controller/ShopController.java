package com.andi.shopapi.shopapi.controller;

import com.andi.shopapi.shopapi.model.Order;
import com.andi.shopapi.shopapi.model.OrderUser;
import com.andi.shopapi.shopapi.model.Product;
import com.andi.shopapi.shopapi.model.User;
import com.andi.shopapi.shopapi.model.request.OrderDeletionRequest;
import com.andi.shopapi.shopapi.model.request.OrderRequest;
import com.andi.shopapi.shopapi.model.request.ProductCreationRequest;
import com.andi.shopapi.shopapi.model.request.ProductDeletionRequest;
import com.andi.shopapi.shopapi.model.request.UserCreationRequest;
import com.andi.shopapi.shopapi.model.request.UserDeletionRequest;
import com.andi.shopapi.shopapi.model.response.UserResponse;
import com.andi.shopapi.shopapi.model.result.OrderResult;
import com.andi.shopapi.shopapi.model.response.ProductResponse;
import com.andi.shopapi.shopapi.model.response.OrderResponse;
import com.andi.shopapi.shopapi.model.response.OrderListResponse;
import com.andi.shopapi.shopapi.service.ShopService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/shop")
@RequiredArgsConstructor
public class ShopController {
    private final ShopService shopService;

    // User
    @GetMapping("/user")
    public ResponseEntity<UserResponse> readUsers(@RequestParam(required = false) String userId) {
        if (userId == null) {
            UserResponse response = new UserResponse();
            response.status = 200;
            response.data = shopService.readUsers();
            response.count = response.data.size();
            return ResponseEntity.ok(response);
        }
        return readUser(userId);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserResponse> readUser(@PathVariable String userId) {
        UserResponse response = new UserResponse();
        User result = shopService.readUserById(userId);
        response.status = 200;
        response.data.add(result);
        response.count = 1;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/user")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserCreationRequest request) {
        UserResponse response = new UserResponse();
        if (request.getEmail().isEmpty() || request.getFirstName().isEmpty() || request.getUserName().isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        User result = shopService.createUser(request);
        response.status = 200;
        response.data.add(result);
        response.message = "Success creating new user.";
        response.count = 1;
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/user")
    public ResponseEntity<UserResponse> deleteUser(@Valid @RequestBody UserDeletionRequest request) {
        UserResponse response = new UserResponse();
        if (request.getUserId().isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        User result = shopService.deleteUser(request.getUserId());
        response.status = 200;
        response.data.add(result);
        response.message = "Success deleting user.";
        response.count = 1;
        return ResponseEntity.ok(response);
    }

    // Product
    @GetMapping("/product")
    public ResponseEntity<ProductResponse> readProducts(@RequestParam(required = false) String productId) {
        if (productId == null) {
            ProductResponse response = new ProductResponse();
            response.status = 200;
            response.data = shopService.readProducts();
            response.count = response.data.size();
            return ResponseEntity.ok(response);
        }
        return readProduct(productId);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ProductResponse> readProduct(@PathVariable String productId) {
        ProductResponse response = new ProductResponse();
        Product result = shopService.readProductById(productId);
        response.status = 200;
        response.data.add(result);
        response.count = 1;
        return ResponseEntity.ok(response);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductCreationRequest request) {
        ProductResponse response = new ProductResponse();
        if (request.getName().isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        Product result = shopService.createProduct(request);
        response.status = 200;
        response.data.add(result);
        response.message = "Success creating new product.";
        response.count = 1;
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/product")
    public ResponseEntity<ProductResponse> deleteProduct(@Valid @RequestBody ProductDeletionRequest request) {
        ProductResponse response = new ProductResponse();
        if (request.getProductId().isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        Product result = shopService.deleteProduct(request.getProductId());
        response.status = 200;
        response.data.add(result);
        response.message = "Success deleting product.";
        response.count = 1;
        return ResponseEntity.ok(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<OrderResponse> readOrder(@PathVariable String orderId) {
        OrderResponse response = new OrderResponse();
        if (orderId.isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        List<Order> result = shopService.readItemsOrderByOrderId(orderId);
        response.status = 200;
        response.entries = result;
        response.count = response.entries.size();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orderlist/{userId}")
    public ResponseEntity<OrderListResponse> readUserOrders(@PathVariable String userId) {
        OrderListResponse response = new OrderListResponse();
        if (userId.isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        List<OrderUser> result = shopService.readOrdersByUser(userId);
        response.status = 200;
        response.data = result;
        response.count = response.data.size();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/order")
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = new OrderResponse();
        if (request.getProductIds().isEmpty() || request.getAmounts().isEmpty()
                || request.getProductIds().size() != request.getAmounts().size()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }
        OrderResult result = shopService.createOrder(request);
        response.status = 200;
        response.orderData = result.orderUser;
        response.entries = result.orders;
        response.message = "Success creating new order.";
        response.count = response.entries.size();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/order")
    public ResponseEntity<OrderResponse> deleteOrder(@Valid @RequestBody OrderDeletionRequest request) {
        OrderResponse response = new OrderResponse();
        if (request.getOrderId().isEmpty()) {
            response.status = 400;
            response.count = 0;
            response.message = "Invalid request.";
            return ResponseEntity.ok(response);
        }

        shopService.deleteOrderUser(request.getOrderId());
        response.status = 200;
        response.message = "Success delete order.";
        response.count = 0;
        return ResponseEntity.ok(response);
    }
}
