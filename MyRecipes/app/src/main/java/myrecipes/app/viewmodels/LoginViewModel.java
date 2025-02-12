package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;

import myrecipes.app.repositories.UserRepository;

public class LoginViewModel extends ViewModel {
    private final UserRepository userRepository;
    private final MutableLiveData<FirebaseUser> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoadingLiveData = new MutableLiveData<>();

    public LoginViewModel() {
        userRepository = new UserRepository();
    }

    public void login(String email, String password) {
        isLoadingLiveData.setValue(true);

        userRepository.loginUser(email, password)
                .addOnSuccessListener(authResult -> {
                    isLoadingLiveData.setValue(false);
                    userLiveData.setValue(authResult.getUser());
                })
                .addOnFailureListener(e -> {
                    isLoadingLiveData.setValue(false);
                    errorLiveData.setValue(e.getMessage());
                });
    }

    public LiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public LiveData<Boolean> getIsLoadingLiveData() {
        return isLoadingLiveData;
    }
}