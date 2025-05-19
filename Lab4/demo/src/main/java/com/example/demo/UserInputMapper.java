package com.example.demo;

public class UserInputMapper {
    public static ProductSearchQuery mapToQuery(UserInput input) {
        return new ProductSearchQuery(input.getProducts(), input.getMaxCalories());
    }
}
