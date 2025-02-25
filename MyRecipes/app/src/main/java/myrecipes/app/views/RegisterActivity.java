/**
 * Handles new user registration process.
 * Demonstrates form validation, Firebase Authentication, and user data storage.
 */
package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import myrecipes.app.databinding.ActivityRegisterBinding;
import myrecipes.app.utils.ValidationResult;
import myrecipes.app.utils.ValidationUtils;
import myrecipes.app.viewmodels.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {
    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;

    /**
     * Initializes the activity, sets up data binding and ViewModel.
     * Shows proper activity setup with MVVM pattern.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        // Setup observers and click listeners
        observeViewModel();
        binding.registerButton.setOnClickListener(v -> validateAndRegister());
    }

    /**
     * Sets up observers for ViewModel's LiveData objects.
     * Demonstrates proper handling of registration state changes.
     */
    private void observeViewModel() {
        // Observe error states
        viewModel.getErrorLiveData().observe(this, error ->
                Toast.makeText(this, error, Toast.LENGTH_LONG).show());

        // Observe registration success
        viewModel.getRegistrationSuccessLiveData().observe(this, success -> {
            if (success) {
                Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        });

        // Observe loading state
        viewModel.getIsLoadingLiveData().observe(this, isLoading -> {
            binding.registerButton.setEnabled(!isLoading);
        });
    }

    /**
     * Validates user input before attempting registration.
     * Shows comprehensive form validation implementation.
     */
    private void validateAndRegister() {
        String fullName = binding.fullNameEditText.getText().toString().trim();
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordEditText.getText().toString().trim();
        String phone = binding.phoneEditText.getText().toString().trim();
        String address = binding.addressEditText.getText().toString().trim();

        if (validateInputs(fullName, email, password, confirmPassword, phone, address)) {
            viewModel.registerUser(fullName, email, password, phone, address);
        }
    }

    /**
     * Validates all registration form inputs.
     * Shows proper input validation with specific error messages.
     *
     * @return true if all inputs are valid, false otherwise
     */
    private boolean validateInputs(String fullName, String email, String password,
                                   String confirmPassword, String phone, String address) {
        // Validate full name
        ValidationResult nameValidation = ValidationUtils.validateFullName(fullName);
        if (!nameValidation.isValid()) {
            binding.fullNameEditText.setError(nameValidation.getErrorMessage());
            return false;
        }

        // Validate email format
        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            binding.emailEditText.setError(emailValidation.getErrorMessage());
            return false;
        }

        // Validate password strength
        ValidationResult passwordValidation = ValidationUtils.validatePassword(password);
        if (!passwordValidation.isValid()) {
            binding.passwordEditText.setError(passwordValidation.getErrorMessage());
            return false;
        }

        // Validate password confirmation
        ValidationResult passwordMatchValidation = ValidationUtils.validatePasswordMatch(password, confirmPassword);
        if (!passwordMatchValidation.isValid()) {
            binding.confirmPasswordEditText.setError(passwordMatchValidation.getErrorMessage());
            return false;
        }

        // Validate phone number
        ValidationResult phoneValidation = ValidationUtils.validatePhone(phone);
        if (!phoneValidation.isValid()) {
            binding.phoneEditText.setError(phoneValidation.getErrorMessage());
            return false;
        }

        // Validate address
        ValidationResult addressValidation = ValidationUtils.validateAddress(address);
        if (!addressValidation.isValid()) {
            binding.addressEditText.setError(addressValidation.getErrorMessage());
            return false;
        }

        return true;
    }

    /**
     * Cleanup to prevent memory leaks.
     * Shows proper handling of view binding cleanup.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}