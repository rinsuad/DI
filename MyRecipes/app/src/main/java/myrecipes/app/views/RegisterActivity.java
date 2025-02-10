package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import myrecipes.app.R;
import myrecipes.app.utils.ValidationResult;
import myrecipes.app.utils.ValidationUtils;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private EditText fullNameEditText, emailEditText, passwordEditText, confirmPasswordEditText, phoneEditText, addressEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Inicializar Firebase Auth y Database
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Vincular vistas
        fullNameEditText = findViewById(R.id.fullNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        addressEditText = findViewById(R.id.addressEditText);
        registerButton = findViewById(R.id.registerButton);

        // Configurar botón de registro
        registerButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String fullName = fullNameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();
        String phone = phoneEditText.getText().toString().trim();
        String address = addressEditText.getText().toString().trim();

        // Validate all fields
        ValidationResult nameValidation = ValidationUtils.validateFullName(fullName);
        if (!nameValidation.isValid()) {
            fullNameEditText.setError(nameValidation.getErrorMessage());
            return;
        }

        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            emailEditText.setError(emailValidation.getErrorMessage());
            return;
        }

        ValidationResult passwordValidation = ValidationUtils.validatePassword(password);
        if (!passwordValidation.isValid()) {
            passwordEditText.setError(passwordValidation.getErrorMessage());
            return;
        }

        ValidationResult passwordMatchValidation = ValidationUtils.validatePasswordMatch(password, confirmPassword);
        if (!passwordMatchValidation.isValid()) {
            confirmPasswordEditText.setError(passwordMatchValidation.getErrorMessage());
            return;
        }

        ValidationResult phoneValidation = ValidationUtils.validatePhone(phone);
        if (!phoneValidation.isValid()) {
            phoneEditText.setError(phoneValidation.getErrorMessage());
            return;
        }

        ValidationResult addressValidation = ValidationUtils.validateAddress(address);
        if (!addressValidation.isValid()) {
            addressEditText.setError(addressValidation.getErrorMessage());
            return;
        }

        // If all validations pass, proceed with registration
        registerUserInFirebase(fullName, email, password, phone, address);
    }

    private void registerUserInFirebase(String fullName, String email, String password,
                                        String phone, String address) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            saveUserData(firebaseUser.getUid(), fullName, email, phone, address);
                        }
                    } else {
                        handleRegistrationError(task.getException());
                    }
                });
    }

    private void saveUserData(String userId, String fullName, String email,
                              String phone, String address) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("fullName", fullName);
        userMap.put("email", email);
        userMap.put("phone", phone);
        userMap.put("address", address);

        mDatabase.child("users").child(userId).setValue(userMap)
                .addOnCompleteListener(dbTask -> {
                    if (dbTask.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this,
                                "Registration successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        handleDatabaseError(dbTask.getException());
                    }
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
        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private void handleDatabaseError(Exception exception) {
        Toast.makeText(RegisterActivity.this,
                "Error saving user data: " + exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}