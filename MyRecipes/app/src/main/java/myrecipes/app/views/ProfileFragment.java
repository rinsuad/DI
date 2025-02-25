/**
 * Handles user profile management including:
 * - Password changes
 * - Profile information updates
 * - Dark mode preferences
 * Demonstrates Firebase authentication and real-time database updates.
 */
package myrecipes.app.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;
import java.util.HashMap;
import java.util.Map;
import myrecipes.app.databinding.FragmentProfileBinding;
import myrecipes.app.utils.ValidationResult;
import myrecipes.app.utils.ValidationUtils;
import myrecipes.app.viewmodels.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private static final String PREFS_NAME = "AppSettings";
    private static final String DARK_MODE_KEY = "dark_mode_enabled";

    /**
     * Inflates the fragment layout using View Binding.
     * Shows proper fragment initialization with view binding.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Sets up ViewModel and UI components after view creation.
     * Demonstrates proper lifecycle-aware setup and preference handling.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);

        // Load dark mode preference
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean(DARK_MODE_KEY, false);
        binding.darkModeSwitch.setChecked(isDarkMode);

        // Setup click listeners
        setupClickListeners();
    }

    /**
     * Sets up click listeners for all interactive UI elements.
     * Shows proper organization of UI interaction handling.
     */
    private void setupClickListeners() {
        binding.changePasswordButton.setOnClickListener(v -> changePassword());
        binding.darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) ->
                toggleDarkMode(isChecked));
        binding.updateProfileButton.setOnClickListener(v -> updateProfile());
    }

    /**
     * Handles password change functionality.
     * Demonstrates Firebase authentication state management and re-authentication.
     */
    private void changePassword() {
        String currentPass = binding.currentPasswordEditText.getText().toString();
        String newPass = binding.newPasswordEditText.getText().toString();

        // Validate current password
        if (TextUtils.isEmpty(currentPass)) {
            binding.currentPasswordEditText.setError("La contraseña actual es obligatoria");
            return;
        }

        // Validate new password
        ValidationResult passwordValidation = ValidationUtils.validatePassword(newPass);
        if (!passwordValidation.isValid()) {
            binding.newPasswordEditText.setError(passwordValidation.getErrorMessage());
            return;
        }

        // Re-authenticate user before changing password
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPass);
            user.reauthenticate(credential)
                    .addOnSuccessListener(aVoid -> updatePassword(user, newPass))
                    .addOnFailureListener(this::handleAuthError);
        }
    }

    /**
     * Updates user password after successful re-authentication.
     * Shows proper error handling and user feedback.
     */
    private void updatePassword(FirebaseUser user, String newPassword) {
        user.updatePassword(newPassword)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(),
                            "Contraseña actualizada exitosamente", Toast.LENGTH_SHORT).show();
                    binding.currentPasswordEditText.setText("");
                    binding.newPasswordEditText.setText("");
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(),
                                "Error al actualizar la contraseña: " + e.getMessage(),
                                Toast.LENGTH_LONG).show());
    }

    /**
     * Handles authentication errors with specific error messages.
     * Shows proper error handling with user-friendly messages.
     */
    private void handleAuthError(Exception e) {
        String errorMessage = "Falló la autenticación: ";
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage += "La contraseña actual es incorrecta";
        } else {
            errorMessage += e.getMessage();
        }
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * Updates user profile information.
     * Demonstrates form validation and Firebase database updates.
     */
    private void updateProfile() {
        String name = viewModel.getName().getValue();
        String email = viewModel.getEmail().getValue();

        if (name == null || email == null) {
            Toast.makeText(requireContext(), "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validate name format
        ValidationResult nameValidation = ValidationUtils.validateFullName(name);
        if (!nameValidation.isValid()) {
            Snackbar.make(binding.getRoot(), nameValidation.getErrorMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }

        // Validate email format
        ValidationResult emailValidation = ValidationUtils.validateEmail(email);
        if (!emailValidation.isValid()) {
            Snackbar.make(binding.getRoot(), emailValidation.getErrorMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }

        // Update profile in Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            updateUserProfile(user, name, email);
        }
    }

    /**
     * Updates user profile data in Firebase Realtime Database.
     * Shows proper database update pattern with success/failure handling.
     *
     * @param user Current Firebase user
     * @param name Updated user name
     * @param email Updated email address
     */
    private void updateUserProfile(FirebaseUser user, String name, String email) {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid());

        Map<String, Object> updates = new HashMap<>();
        updates.put("fullName", name);
        updates.put("email", email);

        userRef.updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Perfil actualizado exitosamente", Toast.LENGTH_SHORT).show();
                    viewModel.getName().setValue(name);
                    viewModel.getEmail().setValue(email);
                })
                .addOnFailureListener(e ->
                        Toast.makeText(requireContext(),
                                "Error al actualizar el perfil: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }

    /**
     * Toggles dark mode for the application.
     * Shows proper theme switching implementation.
     *
     * @param enableDarkMode True to enable dark mode, false to disable
     */
    private void toggleDarkMode(boolean enableDarkMode) {
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).toggleDarkMode(enableDarkMode);
        }
    }

    /**
     * Cleanup to prevent memory leaks.
     * Shows proper view binding cleanup in fragments.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}