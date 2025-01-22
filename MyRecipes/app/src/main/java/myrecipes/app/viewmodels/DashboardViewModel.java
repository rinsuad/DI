package myrecipes.app.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import myrecipes.app.models.Recipe;
import myrecipes.app.repositories.RecipeRepository;
import java.util.List;

public class DashboardViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private LiveData<List<Recipe>> recipes;

    public DashboardViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}