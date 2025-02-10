package myrecipes.app.utils;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidationUtils {
    public static ValidationResult validateFullName(String fullName) {
        if (TextUtils.isEmpty(fullName)) {
            return new ValidationResult(false, "El nombre es obligatorio");
        }
        if (fullName.length() < 3) {
            return new ValidationResult(false, "El nombre debe tener al menos 3 caracteres");
        }
        if (!fullName.matches("[a-zA-Z ]+")) {
            return new ValidationResult(false, "El nombre solo puede contener letras y espacios");
        }
        return new ValidationResult(true, null);
    }

    public static ValidationResult validateEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return new ValidationResult(false, "El correo electrónico es obligatorio");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return new ValidationResult(false, "Por favor, ingrese un correo electrónico válido");
        }
        return new ValidationResult(true, null);
    }

    public static ValidationResult validatePassword(String password) {
        if (TextUtils.isEmpty(password)) {
            return new ValidationResult(false, "La contraseña es obligatoria");
        }
        if (password.length() < 8) {
            return new ValidationResult(false, "La contraseña debe tener al menos 8 caracteres");
        }
        if (!password.matches(".*[A-Z].*")) {
            return new ValidationResult(false, "La contraseña debe contener al menos una letra mayúscula");
        }
        if (!password.matches(".*[a-z].*")) {
            return new ValidationResult(false, "La contraseña debe contener al menos una letra minúscula");
        }
        if (!password.matches(".*[0-9].*")) {
            return new ValidationResult(false, "La contraseña debe contener al menos un número");
        }
        return new ValidationResult(true, null);
    }

    public static ValidationResult validatePasswordMatch(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return new ValidationResult(false, "Las contraseñas no coinciden");
        }
        return new ValidationResult(true, null);
    }

    public static ValidationResult validatePhone(String phone) {
        if (TextUtils.isEmpty(phone)) {
            return new ValidationResult(false, "El número de teléfono es obligatorio");
        }
        if (!phone.matches("\\d{10,}")) {
            return new ValidationResult(false, "Por favor, ingrese un número de teléfono válido (al menos 10 dígitos)");
        }
        return new ValidationResult(true, null);
    }

    public static ValidationResult validateAddress(String address) {
        if (TextUtils.isEmpty(address)) {
            return new ValidationResult(false, "La dirección es obligatoria");
        }
        if (address.length() < 10) {
            return new ValidationResult(false, "Por favor, ingrese una dirección completa (al menos 10 caracteres)");
        }
        return new ValidationResult(true, null);
    }
}