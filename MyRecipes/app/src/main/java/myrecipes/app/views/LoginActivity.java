/**
 * Handles user authentication through Firebase.
 * Implements MVVM architecture with data binding for login functionality.
 */
package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import myrecipes.app.databinding.ActivityLoginBinding;
import myrecipes.app.utils.ValidationResult;
import myrecipes.app.utils.ValidationUtils;
import myrecipes.app.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;

    /**
     * Initializes the activity, sets up data binding and ViewModel.
     * Uses ViewBinding for type-safe view access.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel using ViewModelProvider
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setupClickListeners();
        observeViewModel();
    }

    /**
     * Sets up click listeners for login and registration buttons.
     * Demonstrates separation of UI interaction logic.
     */
    private void setupClickListeners() {
        binding.loginButton.setOnClickListener(v -> validateAndLogin());
        binding.registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Observes ViewModel's LiveData objects to react to authentication state changes.
     * Shows how MVVM pattern handles UI updates based on data changes.
     */
    private void observeViewModel() {
        // Observe authentication success
        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        // Observe errors
        viewModel.getErrorLiveData().observe(this, error ->
                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show());

        // Observe loading state
        viewModel.getIsLoadingLiveData().observe(this, isLoading -> {
            binding.loginButton.setEnabled(!isLoading);
            binding.registerButton.setEnabled(!isLoading);
        });
    }

    /**
     * Validates user input before attempting login.
     * Shows form validation pattern with immediate feedback.
     */
    private void validateAndLogin() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        // Validate email format
        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            binding.emailEditText.setError(emailValidation.getErrorMessage());
            return;
        }

        // Validate password presence
        if (password.isEmpty()) {
            binding.passwordEditText.setError("Contraseña requerida");
            return;
        }

        // Attempt login through ViewModel
        viewModel.login(email, password);
    }

    /**
     * Cleanup when activity is destroyed.
     * Prevents memory leaks by nullifying binding.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}