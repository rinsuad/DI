package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.DashboardRepository;
import java.util.List;

/**
 * This ViewModel manages the UI state for the dashboard/home screen of the app.
 * It follows the MVVM (Model-View-ViewModel) pattern to separate UI logic from data handling.
 */

public class DashboardViewModel extends ViewModel {
    // LiveData to hold the list of recipes - MutableLiveData internally, exposed as LiveData
    private final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    // Loading state indicator for UI feedback
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    // Repository instance for data operations
    private final DashboardRepository dashboardRepository;

    /**
     * Constructor initializes the repository and triggers initial data load.
     * Loading starts automatically when ViewModel is created.
     */
    public DashboardViewModel() {
        dashboardRepository = new DashboardRepository();
        loadRecipes();
    }

    /**
     * Provides read-only access to recipe data.
     * Returns LiveData instead of MutableLiveData to prevent modifications from outside.
     */
    public LiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }

    /**
     * Provides read-only access to loading state.
     * UI can observe this to show/hide loading indicators.
     */
    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    /**
     * Initiates recipe loading from repository.
     * Sets loading state and triggers repository call.
     */
    private void loadRecipes() {
        isLoading.setValue(true);
        dashboardRepository.getRecipe(recipeLiveData);
    }
}