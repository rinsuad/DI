package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.DashboardRepository;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    private final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    private final DashboardRepository dashboardRepository;

    public DashboardViewModel() {
        dashboardRepository = new DashboardRepository();
        loadProducts();
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }

    private void loadProducts() {
        dashboardRepository.getRecipe(recipeLiveData);
    }
}