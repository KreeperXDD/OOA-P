package com.example.demo;

import java.util.List;

public class Recipe {
    private final String title;
    private final List<String> ingredients;
    private final int cookingTime;
    private final String description;

    public Recipe(String title, List<String> ingredients, int cookingTime, String description) {
        this.title = title;
        this.ingredients = ingredients;
        this.cookingTime = cookingTime;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public int getCookingTime() {
        return cookingTime;
    }

    public String getDescription() {
        return description;
    }
}
