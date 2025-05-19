package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RecipeDetailController {
    @FXML
    private Label titleLabel;
    @FXML
    private ListView<String> ingredientsList;
    @FXML
    private Label cookingTimeLabel;
    @FXML
    private TextArea descriptionArea;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setRecipe(Recipe recipe) {
        titleLabel.setText(recipe.getTitle());
        ingredientsList.getItems().addAll(recipe.getIngredients());
        cookingTimeLabel.setText("Время приготовления: " + recipe.getCookingTime() + " минут");
        descriptionArea.setText(recipe.getDescription());
    }

    @FXML
    private void onClose() {
        stage.close();
    }
}
