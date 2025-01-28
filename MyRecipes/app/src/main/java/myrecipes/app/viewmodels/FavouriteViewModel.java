package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.FavouriteRepository;

public class FavouriteViewModel extends ViewModel {
    private final MutableLiveData<List<Recipe>> recipeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isFavoriteLiveData = new MutableLiveData<>();
    private final FavouriteRepository favouriteRepository;

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

    private void loadFavorites() {
        favouriteRepository.getFavourites(recipeLiveData);
    }

    public void toggleFavorite(String recipeId, boolean isFavorite) {
        favouriteRepository.toggleFavorite(recipeId, isFavorite);
    }

    public void checkIsFavorite(String recipeId) {
        favouriteRepository.checkIsFavorite(recipeId, isFavoriteLiveData);
    }
}
