package com.example.demo;

import java.util.List;

public class UserInput {
    private final List<String> products;
    private final int maxCalories;

    public UserInput(List<String> products, int maxCalories) {
        this.products = products;
        this.maxCalories = maxCalories;
    }

    public List<String> getProducts() {
        return products;
    }

    public int getMaxCalories() {
        return maxCalories;
    }
}
