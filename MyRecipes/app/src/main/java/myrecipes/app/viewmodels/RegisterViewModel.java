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

public class RegisterViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> registrationSuccessLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

    public RegisterViewModel() {
        userRepository = new UserRepository();
    }

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
