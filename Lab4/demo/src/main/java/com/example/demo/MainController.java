package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    private TextField productField;
    @FXML
    private Spinner<Integer> calorieSpinner;
    @FXML
    private ListView<String> productList;
    @FXML
    private ListView<String> recipeList;

    private final ObservableList<String> products = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        productList.setItems(products);
        calorieSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(100, 5000, 2000));
    }

    @FXML
    private void onAddProduct() {
        String product = productField.getText().trim();
        if (!product.isEmpty()) {
            products.add(product);
            productField.clear();
        }
    }

    @FXML
    private void onSearchRecipes() {
        UserInput input = new UserInput(new ArrayList<>(products), calorieSpinner.getValue());
        ProductSearchQuery query = UserInputMapper.mapToQuery(input);
        List<Recipe> recipes = DatabaseManager.findRecipes(query);
        recipeList.getItems().clear();
        recipes.forEach(r -> recipeList.getItems().add(r.getTitle()));
    }

    @FXML
    private void onRecipeSelected() {
        String selected = recipeList.getSelectionModel().getSelectedItem();
        if (selected != null) {
            Recipe recipe = DatabaseManager.getRecipeByTitle(selected);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/recipe-detail.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Рецепт: " + recipe.getTitle());
                stage.setScene(new Scene(loader.load()));

                RecipeDetailController controller = loader.getController();
                controller.setRecipe(recipe);
                controller.setStage(stage);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}