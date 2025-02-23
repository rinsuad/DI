package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.FavouriteRepository;


/**
 * Manages the UI state for the user's favorite recipes screen.
 * Handles loading and toggling of favorite recipes.
 */
public class FavouriteViewModel extends ViewModel {
    private final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavoriteLiveData = new MutableLiveData<>();
    private final FavouriteRepository favouriteRepository;

    /**
     * Constructor initializes repository and triggers initial load
     * of favorite recipes.
     */
    public FavouriteViewModel() {
        favouriteRepository = new FavouriteRepository();
        loadFavorites();
    }

    public LiveData<List<Recipe>> getRecipeLiveData() {
        return recipeLiveData;
    }

    public LiveData<Boolean> getIsFavoriteLiveData() {
        return isFavoriteLiveData;
    }

    /**
     * Loads user's favorite recipes from repository
     */
    private void loadFavorites() {
        favouriteRepository.getFavourites(recipeLiveData);
    }

    /**
     * Toggles favorite status for a specific recipe
     */
    public void toggleFavorite(String recipeId, boolean isFavorite) {
        favouriteRepository.toggleFavorite(recipeId, isFavorite);
    }

    /**
     * Checks favorite status of a specific recipe
     */
    public void checkIsFavorite(String recipeId) {
        favouriteRepository.checkIsFavorite(recipeId, isFavoriteLiveData);
    }
}
