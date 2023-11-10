package com.andi.shopapi.shopapi.model.request;

import lombok.Data;

@Data
public class ProductCreationRequest {
    private String name;
    private Integer basePrice;
}
