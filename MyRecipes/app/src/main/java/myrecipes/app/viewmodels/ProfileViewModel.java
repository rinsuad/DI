package myrecipes.app.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> email = new MutableLiveData<>();

    public MutableLiveData<String> getName() { return name; }
    public MutableLiveData<String> getEmail() { return email; }
}