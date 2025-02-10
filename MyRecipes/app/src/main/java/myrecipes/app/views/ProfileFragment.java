package myrecipes.app.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.LiveData;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import myrecipes.app.R;
import myrecipes.app.databinding.FragmentProfileBinding;
import myrecipes.app.utils.ValidationResult;
import myrecipes.app.utils.ValidationUtils;
import myrecipes.app.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get initial dark mode state from SharedPreferences
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean("darkMode", false);
        binding.darkModeSwitch.setChecked(isDarkMode);

        // Setup click listeners
        binding.changePasswordButton.setOnClickListener(v -> changePassword());
        binding.darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> toggleDarkMode(isChecked));
        binding.updateProfileButton.setOnClickListener(v -> updateProfile());
    }

    private void changePassword() {
        String currentPass = binding.currentPasswordEditText.getText().toString();
        String newPass = binding.newPasswordEditText.getText().toString();

        // Validate current password
        if (TextUtils.isEmpty(currentPass)) {
            binding.currentPasswordEditText.setError("Current password is required");
            return;
        }

        // Validate new password
        ValidationResult passwordValidation = ValidationUtils.validatePassword(newPass);
        if (!passwordValidation.isValid()) {
            binding.newPasswordEditText.setError(passwordValidation.getErrorMessage());
            return;
        }

        // If validation passes, proceed with password change
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            // First re-authenticate
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);
            user.reauthenticate(credential)
                    .addOnSuccessListener(aVoid -> updatePassword(user, newPass))
                    .addOnFailureListener(e -> handleAuthError(e));
        }
    }

    private void updatePassword(FirebaseUser user, String newPassword) {
        user.updatePassword(newPassword)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(),
                            "Password updated successfully", Toast.LENGTH_SHORT).show();
                    binding.currentPasswordEditText.setText("");
                    binding.newPasswordEditText.setText("");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(),
                                "Error updating password: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void handleAuthError(Exception e) {
        String errorMessage = "Authentication failed: ";
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage += "Current password is incorrect";
        } else {
            errorMessage += e.getMessage();
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    private void updateProfile() {
        String name = viewModel.getName().getValue();
        String email = viewModel.getEmail().getValue();

        if (name == null || email == null) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        ValidationResult nameValidation = ValidationUtils.validateFullName(name);
        if (!nameValidation.isValid()) {
            Snackbar.make(binding.getRoot(), nameValidation.getErrorMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }

        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            Snackbar.make(binding.getRoot(), emailValidation.getErrorMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }

        // Proceed with profile update
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            updateUserProfile(user, name, email);
        }
    }

    private void updateUserProfile(FirebaseUser user, String name, String email) {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid());

        Map<String, Object> updates = new HashMap<>();
        updates.put("fullName", name);
        updates.put("email", email);

        userRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    // Update ViewModel values if needed
                    viewModel.getName().setValue(name);
                    viewModel.getEmail().setValue(email);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(),
                                "Error updating profile: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    private void toggleDarkMode(boolean enableDarkMode) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("darkMode", enableDarkMode).apply();

        AppCompatDelegate.setDefaultNightMode(
                enableDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
        requireActivity().recreate();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}