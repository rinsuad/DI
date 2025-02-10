package myrecipes.app.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import myrecipes.app.R;
import myrecipes.app.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && !newPass.isEmpty()) {
            AuthCredential credential = EmailAuthProvider
                    .getCredential(user.getEmail(), currentPass);

            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    user.updatePassword(newPass).addOnCompleteListener(updateTask -> {
                        if (updateTask.isSuccessful()) {
                            Toast.makeText(getContext(), "Contraseña cambiada con éxito", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Error al cambiar la contraseña", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getContext(), "La contraseña actual no es correcta", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void toggleDarkMode(boolean enableDarkMode) {
        SharedPreferences prefs = requireActivity().getSharedPreferences("AppConfig", Context.MODE_PRIVATE);
        prefs.edit().putBoolean("darkMode", enableDarkMode).apply();

        AppCompatDelegate.setDefaultNightMode(
                enableDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
        requireActivity().recreate();
    }

    private void updateProfile() {
        // Implement profile update logic
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}