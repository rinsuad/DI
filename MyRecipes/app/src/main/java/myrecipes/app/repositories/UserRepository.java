package myrecipes.app.repositories;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import myrecipes.app.models.User;

public class UserRepository {
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    public UserRepository() {
        // Inicializamos Firebase Auth y Firestore
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    // Método para obtener el usuario actualmente autenticado
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    // Método para guardar los datos del usuario en Firestore
    public void saveUser(User user) {
        db.collection("users").document(user.getUid()).set(user)
                .addOnSuccessListener(aVoid ->
                        System.out.println("Usuario guardado con éxito"))
                .addOnFailureListener(e ->
                        System.err.println("Error al guardar usuario: " + e.getMessage()));
    }

    // Método para cerrar sesión del usuario
    public void logout() {
        auth.signOut();
    }
}