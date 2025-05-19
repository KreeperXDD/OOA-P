package com.example.demo;

import java.util.List;

public class ProductSearchQuery {
    private final List<String> ingredients;
    private final int maxCalories;

    public ProductSearchQuery(List<String> ingredients, int maxCalories) {
        this.ingredients = ingredients;
        this.maxCalories = maxCalories;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public int getMaxCalories() {
        return maxCalories;
    }
}
