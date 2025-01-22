package myrecipes.app.models;

public class Recipe {
    private String title;
    private String description;
    private String imageUrl;

    public Recipe() {
        // Empty constructor for Firebase
    }

    public Recipe(String title, String description, String imageUrl) {
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}