package com.andi.shopapi.shopapi.model;

import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Product {

    @Id
    private String id;

    @NotBlank(message = "Product Name is mandatory")
    private String name;
    
    private Integer basePrice;
    private Instant createdDate;
    private Instant updatedDate;
}
