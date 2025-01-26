package myrecipes.app.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int calories;
    public List<List<Object>> ingredients;
    private List<String> steps;

    public Recipe() {
        // Empty constructor for Firebase
    }

    public Recipe(String id, String title, String description, String imageUrl, int calories, List<List<Object>> ingredients, List<String> steps) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getCalories() {
        return calories;
    }

    public List<String> getIngredientsList() {
        List<String> ingredientStrings = new ArrayList<>();
        if (ingredients != null) {
            for (List<Object> ingredient : ingredients) {
                ingredientStrings.add(ingredient.get(0).toString());
            }
        }
        return ingredientStrings;
    }

    public List<List<Object>> getIngredients() {
        return ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }
}