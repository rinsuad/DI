package myrecipes.app.models;

// Clase User para representar los datos de usuario
public class User {
    private String uid;
    private String email;

    public User() {
        // Constructor vacío requerido para Firebase
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }
}