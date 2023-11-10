package com.andi.shopapi.shopapi.service;

import com.andi.shopapi.shopapi.model.*;
import com.andi.shopapi.shopapi.exception.EntityNotFoundException;
import com.andi.shopapi.shopapi.model.request.OrderRequest;
import com.andi.shopapi.shopapi.model.request.ProductCreationRequest;
import com.andi.shopapi.shopapi.model.request.UserCreationRequest;
import com.andi.shopapi.shopapi.model.result.OrderResult;
import com.andi.shopapi.shopapi.repository.OrderRepository;
import com.andi.shopapi.shopapi.repository.ProductRepository;
import com.andi.shopapi.shopapi.repository.UserRepository;
import com.andi.shopapi.shopapi.repository.OrderUserRepository;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShopService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderUserRepository orderUserRepository;

    // List
    public List<User> readUsers() {
        return userRepository.findAll();
    }

    public List<Product> readProducts() {
        return productRepository.findAll();
    }

    public List<OrderUser> readOrdersByUser(String userId) {
        User user = readUserById(userId);
        List<OrderUser> orders = orderUserRepository.findByUserId(user);
        return orders;
    }

    public List<Order> readOrderEntriesById(String orderId) {
        List<Order> orderEntries = orderRepository.findByOrderId(orderId);
        return orderEntries;
    }

    // Read
    public User readUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new EntityNotFoundException("Cant find any user under given ID");
    }

    public Product readProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            return product.get();
        }
        throw new EntityNotFoundException("Cant find any product under given ID");
    }

    public List<Order> readItemsOrderByOrderId(String orderId) {
        List<Order> items = orderRepository.findByOrderId(orderId);
        if (!items.isEmpty()) {
            return items;
        }
        throw new EntityNotFoundException("Cant find any product under given Order ID");
    }

    public List<OrderUser> readItemsOrderUser(String userId) {
        User user = readUserById(userId);
        List<OrderUser> orders = orderUserRepository.findByUserId(user);
        if (!orders.isEmpty()) {
            return orders;
        }
        throw new EntityNotFoundException("Cant find any orders under this user");
    }

    // Create
    public User createUser(UserCreationRequest request) {
        List<User> user = userRepository.findByEmail(request.getEmail());

        if (!user.isEmpty()) {
            throw new RuntimeException("The email is already registered.");
        }

        User newUser = new User();
        BeanUtils.copyProperties(request, newUser);
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setUserRole(UserRole.MEMBER);
        newUser.setCreatedDate(Instant.now());
        return userRepository.save(newUser);
    }

    public Product createProduct(ProductCreationRequest request) {
        Product newProduct = new Product();
        BeanUtils.copyProperties(request, newProduct);
        newProduct.setCreatedDate(Instant.now());
        return productRepository.save(newProduct);
    }

    public OrderUser createOrderUser(OrderRequest request, Optional<User> user) {
        if (!user.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }

        OrderUser newOrderUser = new OrderUser();
        newOrderUser.setUserId(user.get());
        newOrderUser.setCreatedDate(Instant.now());
        return orderUserRepository.save(newOrderUser);
    }

    public OrderResult createOrder(OrderRequest request) {
        String userId = request.getUserId();
        Integer productLength = request.getProductIds().size();

        if (request.getAmounts().size() != request.getProductIds().size()) {
            throw new RuntimeException("Product and product amounts is not matched.");
        }

        Optional<User> user = userRepository.findById(userId);

        if (!user.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }

        // Create User Order Sessions.
        OrderUser newOrderUserResult = createOrderUser(request, user);

        // Get Order Id.
        String orderUserId = newOrderUserResult.getId();
        List<Order> orderEntries = new ArrayList<>();

        for (int i = 0; i < productLength; i++) {
            Optional<Product> product = productRepository.findById(request.getProductIds().get((i)));
            if (!product.isPresent()) {
                throw new EntityNotFoundException("Cant find any product under given ID");
            }

            Optional<User> userForId = userRepository.findById(userId);
            if (!userForId.isPresent()) {
                throw new EntityNotFoundException("User not present in the database");
            }

            Order newOrder = new Order();
            newOrder.setLabel(request.getLabel());
            newOrder.setAmount(request.getAmounts().get(i));
            newOrder.setOrderId(orderUserId);
            newOrder.setUserId(user.get());
            newOrder.setProductId(product.get());
            newOrder.setCreatedDate(Instant.now());
            orderEntries.add(orderRepository.save(newOrder));
        }

        OrderResult result = new OrderResult();
        result.orderUser = newOrderUserResult;
        result.orders = orderEntries;
        return result;
    }

    // Update
    public User updateUser(String id, UserCreationRequest request) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            throw new EntityNotFoundException("User not present in the database");
        }
        User user = optionalUser.get();
        Integer changeCount = 0;
        if (!request.getLastName().isBlank()) {
            user.setLastName(request.getLastName());
            changeCount += 1;
        }
        if (!request.getFirstName().isBlank()) {
            user.setFirstName(request.getFirstName());
            changeCount += 1;
        }
        if (!request.getUserName().isBlank()) {
            user.setUserName(request.getUserName());
            changeCount += 1;
        }

        if (changeCount > 0) {
            user.setUpdatedDate(Instant.now());
            return userRepository.save(user);
        }
        return user;
    }

    public Product updateProduct(String id, ProductCreationRequest request) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
            throw new EntityNotFoundException("Product not present in the database");
        }
        Product product = optionalProduct.get();
        Integer changeCount = 0;
        if (!request.getName().isBlank()) {
            product.setName(request.getName());
            changeCount += 1;
        }
        product.setBasePrice(request.getBasePrice());
        product.setUpdatedDate(Instant.now());
        return productRepository.save(product);
    }

    // Delete
    public User deleteUser(String id) {
        User user = readUserById(id);
        List<OrderUser> orderUsers = orderUserRepository.findByUserId(user);

        orderUsers.forEach(entries -> {
            deleteOrderUser(entries.getId());
        });

        userRepository.deleteById(id);
        return user;
    }

    public Product deleteProduct(String id) {
        Product product = readProductById(id);
        productRepository.deleteById(id);
        return product;
    }

    public void deleteOrderUser(String id) {
        orderUserRepository.deleteById(id);

        List<Order> orderEntries = orderRepository.findByOrderId(id);
        orderEntries.forEach(entries -> {
            deleteOrder(entries.getId());
        });
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }

}
