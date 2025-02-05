package myrecipes.app.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();
    private MutableLiveData<String> currentPassword = new MutableLiveData<>();
    private MutableLiveData<String> newPassword = new MutableLiveData<>();

    public MutableLiveData<String> getName() { return name; }
    public MutableLiveData<String> getEmail() { return email; }
    public MutableLiveData<String> getCurrentPassword() { return currentPassword; }
    public MutableLiveData<String> getNewPassword() { return newPassword; }

    public void updateProfile() {
        // Implement profile update logic
    }

    public void changePassword() {
        String currentPwd = currentPassword.getValue();
        String newPwd = newPassword.getValue();
        // Implement password change logic
    }
}