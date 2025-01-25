package myrecipes.app.models;

public class Recipe {
    private String id;
    private String title;
    private String description;
    private String imageUrl;

    public Recipe() {
        // Empty constructor for Firebase
    }

    public Recipe(String id, String title, String description, String imageUrl) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {return id;}
    public String setId(String id) {this.id = id;
        return id;
    }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
}