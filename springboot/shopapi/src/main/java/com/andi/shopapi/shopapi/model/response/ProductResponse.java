package com.andi.shopapi.shopapi.model.response;

import java.util.ArrayList;
import java.util.List;

import com.andi.shopapi.shopapi.model.Product;

public class ProductResponse extends DefaultResponse {
    public List<Product> data = new ArrayList<>();
}
