package myrecipes.app.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private int calories;
    private List<List<String>> ingredients;
    private List<String> steps;

    public Recipe() {
        // Empty constructor for Firebase
    }

    public Recipe(String id, String title, String description, String imageUrl, int calories, List<List<String>> ingredients, List<String> steps) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public String getId() {return id;}
    public String setId(String id) {this.id = id;
        return id;
    }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public int getCalories() { return calories; }
    public List<String> getIngredientsList() {
        List<String> ingredientStrings = new ArrayList<>();
        if (ingredients != null) {
            for (List<String> ingredient : ingredients) {
                // Assuming first element is the ingredient name
                ingredientStrings.add(ingredient.get(0));
            }
        }
        return ingredientStrings;
    }
    public List<String> getSteps() { return steps; }
}