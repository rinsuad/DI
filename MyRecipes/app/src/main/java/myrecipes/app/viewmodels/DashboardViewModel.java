package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final DashboardRepository dashboardRepository;

    public DashboardViewModel() {
        dashboardRepository = new DashboardRepository();
        loadRecipes();
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private void loadRecipes() {
        isLoading.setValue(true);
        dashboardRepository.getRecipe(recipeLiveData);
    }
}