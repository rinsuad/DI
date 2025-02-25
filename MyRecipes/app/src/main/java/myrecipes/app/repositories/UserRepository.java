/**
 * Handles all user-related operations including authentication and profile management.
 * Uses both Firebase Authentication and Firestore for user data storage.
 */
package myrecipes.app.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.Map;

public class UserRepository {
    private final FirebaseAuth auth;
    private final FirebaseFirestore db;

    /**
     * Constructor initializes Firebase Authentication and Firestore instances.
     * - FirebaseAuth: handles user authentication
     * - FirebaseFirestore: stores additional user profile data
     */
    public UserRepository() {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * Registers a new user with email and password.
     * Returns a Task that can be observed for success/failure.
     */
    public Task<AuthResult> registerUser(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password);
    }

    /**
     * Saves additional user data to Firestore.
     * This is typically called after successful registration.
     */
    public Task<Void> saveUserData(String userId, Map<String, Object> userData) {
        return db.collection("users").document(userId).set(userData);
    }

    /**
     * Authenticates an existing user with email and password.
     */
    public Task<AuthResult> loginUser(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password);
    }

    /**
     * Returns the currently authenticated user or null if no user is signed in.
     */
    public FirebaseUser getCurrentUser() {
        return auth.getCurrentUser();
    }

    /**
     * Signs out the current user and clears the authentication state.
     */
    public void logout() {
        auth.signOut();
    }
}