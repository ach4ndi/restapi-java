package com.andi.shopapi.shopapi.model.request;

import java.util.List;

import lombok.Data;

@Data
public class OrderRequest {
    private List<String> productIds;
    private List<Integer> amounts;
    private String userId;
    private String label;
}
