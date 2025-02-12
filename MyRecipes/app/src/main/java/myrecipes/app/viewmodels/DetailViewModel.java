package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.DashboardRepository;
import myrecipes.app.repositories.FavouriteRepository;

public class DetailViewModel extends ViewModel {
    private final DashboardRepository repository;
    private final FavouriteRepository favouriteRepository;
    private final MutableLiveData<Recipe> recipe = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavourite = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public DetailViewModel() {
        repository = new DashboardRepository();
        favouriteRepository = new FavouriteRepository();
    }

    public LiveData<Recipe> getRecipe() { return recipe; }
    public LiveData<Boolean> isFavourite() { return isFavourite; }

    public void loadRecipe(String recipeId) {
        loading.setValue(true);
        MutableLiveData<Recipe> tempRecipeLiveData = new MutableLiveData<>();

        tempRecipeLiveData.observeForever(loadedRecipe -> {
            recipe.setValue(loadedRecipe);
            loading.setValue(false);
            // Check if recipe is favourite after loading
            checkIsFavourite(recipeId);
        });

        repository.getSingleRecipe(recipeId, tempRecipeLiveData);
    }

    private void checkIsFavourite(String recipeId) {
        favouriteRepository.checkIsFavorite(recipeId, isFavourite);
    }

    public void toggleFavourite(Recipe recipe) {
        Boolean currentFavouriteStatus = isFavourite.getValue();
        if (currentFavouriteStatus != null) {
            favouriteRepository.toggleFavorite(recipe.getId(), !currentFavouriteStatus);
            isFavourite.setValue(!currentFavouriteStatus);
        }
    }
}