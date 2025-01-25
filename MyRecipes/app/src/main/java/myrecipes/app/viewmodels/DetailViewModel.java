package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.DashboardRepository;

public class DetailViewModel extends ViewModel {
    private final DashboardRepository repository;
    private final MutableLiveData<Recipe> recipe = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public DetailViewModel() {
        repository = new DashboardRepository();
    }

    public LiveData<Recipe> getRecipe() { return recipe; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getLoading() { return loading; }

    public void loadRecipe(String recipeId) {
        loading.setValue(true);
        MutableLiveData<Recipe> tempRecipeLiveData = new MutableLiveData<>();

        tempRecipeLiveData.observeForever(loadedRecipe -> {
            recipe.setValue(loadedRecipe);
            loading.setValue(false);
        });

        repository.getSingleRecipe(recipeId, tempRecipeLiveData);
    }
}