package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();

    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void setLoading(boolean loading) {
        isLoading.setValue(loading);
    }

    public void setError(String errorMessage) {
        error.setValue(errorMessage);
    }

    public void clearError() {
        error.setValue(null);
    }
}