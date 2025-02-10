package myrecipes.app.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import myrecipes.app.utils.ValidationResult;
import myrecipes.app.utils.ValidationUtils;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> currentPassword = new MutableLiveData<>();
    private MutableLiveData<String> newPassword = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    public MutableLiveData<String> getName() { return name; }
    public MutableLiveData<String> getEmail() { return email; }
    public MutableLiveData<String> getCurrentPassword() { return currentPassword; }
    public MutableLiveData<String> getNewPassword() { return newPassword; }
    public MutableLiveData<String> getError() { return error; }
    public MutableLiveData<Boolean> getIsLoading() { return isLoading; }

    public void setError(String errorMessage) {
        error.setValue(errorMessage);
    }

    public void clearError() {
        error.setValue(null);
    }

    public void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    public boolean validateInputs() {
        String nameValue = name.getValue();
        String emailValue = email.getValue();

        if (nameValue == null || emailValue == null) {
            error.setValue("Please fill in all fields");
            return false;
        }

        ValidationResult nameValidation = ValidationUtils.validateFullName(nameValue);
        if (!nameValidation.isValid()) {
            error.setValue(nameValidation.getErrorMessage());
            return false;
        }

        ValidationResult emailValidation = ValidationUtils.validateEmail(emailValue);
        if (!emailValidation.isValid()) {
            error.setValue(emailValidation.getErrorMessage());
            return false;
        }

        return true;
    }
}