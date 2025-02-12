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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        setupClickListeners();
        observeViewModel();
    }

    private void setupClickListeners() {
        binding.loginButton.setOnClickListener(v -> validateAndLogin());
        binding.registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void observeViewModel() {
        viewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                Toast.makeText(LoginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });

        viewModel.getErrorLiveData().observe(this, error ->
                Toast.makeText(LoginActivity.this, "Error: " + error, Toast.LENGTH_LONG).show());

        viewModel.getIsLoadingLiveData().observe(this, isLoading -> {
            binding.loginButton.setEnabled(!isLoading);
            binding.registerButton.setEnabled(!isLoading);
        });
    }

    private void validateAndLogin() {
        String email = binding.emailEditText.getText().toString().trim();
        String password = binding.passwordEditText.getText().toString().trim();

        // Validate email
        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            binding.emailEditText.setError(emailValidation.getErrorMessage());
            return;
        }

        // Validate password is not empty
        if (password.isEmpty()) {
            binding.passwordEditText.setError("Contraseña requerida");
            return;
        }

        viewModel.login(email, password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}