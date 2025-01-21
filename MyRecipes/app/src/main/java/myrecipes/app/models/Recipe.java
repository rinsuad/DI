package myrecipes.app.models;

import java.util.List;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private String imageUrl;
    private String category;
    private int calories;
    private List<List<String>> ingredients;
    private List<String> steps;

    // Constructor vacío requerido para Firebase
    public Recipe() {}

    public Recipe(String id, String title, String description, String imageUrl,
                  String category, int calories, List<List<String>> ingredients, List<String> steps) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.calories = calories;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public int getCalories() { return calories; }
    public void setCalories(int calories) { this.calories = calories; }
    public List<List<String>> getIngredients() { return ingredients; }
    public void setIngredients(List<List<String>> ingredients) { this.ingredients = ingredients; }
    public List<String> getSteps() { return steps; }
    public void setSteps(List<String> steps) { this.steps = steps; }
}

