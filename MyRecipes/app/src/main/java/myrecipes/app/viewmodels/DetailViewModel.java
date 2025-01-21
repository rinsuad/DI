package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.RecipeRepository;
import myrecipes.app.utils.ViewState;

public class DetailViewModel extends ViewModel {
    private final RecipeRepository recipeRepository;
    private final MutableLiveData<ViewState<Recipe>> recipeState;

    public DetailViewModel() {
        recipeRepository = RecipeRepository.getInstance();
        recipeState = new MutableLiveData<>();
    }

    public void loadRecipe(String recipeId) {
        recipeState.setValue(new ViewState.Loading<>());

        // Observar el LiveData que retorna el repository
        recipeRepository.getRecipeById(recipeId).observeForever(recipe -> {
            if (recipe != null) {
                recipeState.setValue(new ViewState.Success<>(recipe));
            } else {
                recipeState.setValue(new ViewState.Error<>("Recipe not found"));
            }
        });
    }

    public LiveData<ViewState<Recipe>> getRecipeState() {
        return recipeState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}