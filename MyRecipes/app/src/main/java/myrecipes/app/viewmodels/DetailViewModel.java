/*package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.DashboardRepository;

public class DetailViewModel extends ViewModel {
    private final DashboardRepository repository;
    private final MutableLiveData<Recipe> recipe;
    private final MutableLiveData<String> error;
    private final MutableLiveData<Boolean> loading;

    public DetailViewModel() {
        repository = new DashboardRepository();
        recipe = new MutableLiveData<>();
        error = new MutableLiveData<>();
        loading = new MutableLiveData<>();
    }

    public LiveData<Recipe> getRecipe() { return recipe; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoading() { return loading; }

    public void loadRecipe(String recipeId) {
        loading.setValue(true);
        repository.getRecipe(recipeId)
                .addOnSuccessListener(result -> {
                    recipe.setValue(result);
                    loading.setValue(false);
                })
                .addOnFailureListener(e -> {
                    error.setValue(e.getMessage());
                    loading.setValue(false);
                });
    }
}*/