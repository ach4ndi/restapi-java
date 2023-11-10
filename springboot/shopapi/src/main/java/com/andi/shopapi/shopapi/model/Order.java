package com.andi.shopapi.shopapi.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Order {

    @Id
    private String id;

    @NotBlank(message = "OrderID is mandatory")
    private String orderId;
    private String label;
    private Instant createdDate;
    private Instant updatedDate;
    private Integer amount;

    @DBRef
    @NotBlank(message = "UserID is mandatory")
    private User userId;

    @DBRef
    @NotBlank(message = "ProductID is mandatory")
    private Product productId;

}
