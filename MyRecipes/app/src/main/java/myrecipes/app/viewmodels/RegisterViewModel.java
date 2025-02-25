package myrecipes.app.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

import myrecipes.app.repositories.UserRepository;
/**
 * Manages user registration process and related UI state.
 * Handles both user authentication and profile data creation.
 */
public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;
    // Error messages for registration process
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    // Indicates successful registration
    private final MutableLiveData<Boolean> registrationSuccessLiveData = new MutableLiveData<>();
    // Loading state for UI feedback
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

    public RegisterViewModel() {
        userRepository = new UserRepository();
    }

    /**
     * Initiates user registration process.
     * Handles both authentication and user profile creation.
     *
     * @param fullName User's full name
     * @param email User's email address
     * @param password User's chosen password
     * @param phone User's phone number
     * @param address User's address
     */
    public void registerUser(String fullName, String email, String password,
                             String phone, String address) {
        isLoadingLiveData.setValue(true);

        userRepository.registerUser(email, password)
                .addOnSuccessListener(authResult -> {
                    FirebaseUser firebaseUser = authResult.getUser();
                    if (firebaseUser != null) {
                        saveUserData(firebaseUser.getUid(), fullName, email, phone, address);
                    }
                })
                .addOnFailureListener(e -> {
                    isLoadingLiveData.setValue(false);
                    handleRegistrationError(e);
                });
    }

    /**
     * Saves additional user profile data after successful authentication.
     * Creates user document in Firestore.
     */
    private void saveUserData(String userId, String fullName, String email,
                              String phone, String address) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("fullName", fullName);
        userData.put("email", email);
        userData.put("phone", phone);
        userData.put("address", address);

        userRepository.saveUserData(userId, userData)
                .addOnSuccessListener(aVoid -> {
                    isLoadingLiveData.setValue(false);
                    registrationSuccessLiveData.setValue(true);
                })
                .addOnFailureListener(e -> {
                    isLoadingLiveData.setValue(false);
                    errorLiveData.setValue("Error saving user data: " + e.getMessage());
                });
    }

    /**
     * Handles different types of registration errors.
     * Provides specific error messages based on the exception type.
     */
    private void handleRegistrationError(Exception exception) {
        String errorMessage = "Registration failed: ";
        if (exception instanceof FirebaseAuthWeakPasswordException) {
            errorMessage += "Password is too weak";
        } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage += "Invalid email format";
        } else if (exception instanceof FirebaseAuthUserCollisionException) {
            errorMessage += "Email already in use";
        } else {
            errorMessage += exception.getMessage();
        }
        errorLiveData.setValue(errorMessage);
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getRegistrationSuccessLiveData() {
        return registrationSuccessLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }
}