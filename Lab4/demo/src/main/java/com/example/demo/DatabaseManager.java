package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:recipes.db";

    static {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS recipes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT," +
                    "ingredients TEXT," +
                    "time_minutes INTEGER," +
                    "calories INTEGER," +
                    "description TEXT)");

            stmt.execute("INSERT INTO recipes (title, ingredients, time_minutes, calories, description) VALUES " +
                    "('Овощной салат', 'помидор,огурец,соль,масло', 10, 150, 'Порезать овощи, добавить соль и масло')," +
                    "('Гречка с курицей', 'гречка,курица,соль', 30, 400, 'Отварить гречку и курицу, смешать');");
            System.out.print(new java.io.File("recipe-detail.fxml").getAbsolutePath());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Recipe> findRecipes(ProductSearchQuery query) {
        List<Recipe> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM recipes WHERE calories <= ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, query.getMaxCalories());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String ingredientsStr = rs.getString("ingredients");
                int time = rs.getInt("time_minutes");
                String description = rs.getString("description");
                List<String> ingredients = Arrays.asList(ingredientsStr.split(","));
                if (query.getIngredients().stream().allMatch(ingredients::contains)) {
                    result.add(new Recipe(title, ingredients, time, description));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Recipe getRecipeByTitle(String title) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM recipes WHERE title = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                List<String> ingredients = Arrays.asList(rs.getString("ingredients").split(","));
                return new Recipe(
                        rs.getString("title"),
                        ingredients,
                        rs.getInt("time_minutes"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void removeDuplicateRecipes() {
        String sql = "DELETE FROM recipes " +
                "WHERE id NOT IN (" +
                "SELECT MIN(id) FROM recipes GROUP BY title, ingredients, time_minutes, calories, description" +
                ")";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
