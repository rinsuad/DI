package myrecipes.app.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Manages user profile data and UI state.
 * Handles display and updates of user profile information.
 */
public class ProfileViewModel extends ViewModel {
    // User profile fields
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();

    public MutableLiveData<String> getName() { return name; }
    public MutableLiveData<String> getEmail() { return email; }
}