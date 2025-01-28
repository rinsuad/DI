package myrecipes.app.views;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import java.util.List;

import myrecipes.app.R;
import myrecipes.app.databinding.ActivityDetailBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DetailViewModel;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel viewModel;
    private ActivityDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setupToolbar();
        setupViewModel();
        setupFavouriteFab();
    }

    private void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupFavouriteFab() {
        binding.fabFavourite.setOnClickListener(v -> {
            Recipe currentRecipe = viewModel.getRecipe().getValue();
            if (currentRecipe != null) {
                viewModel.toggleFavourite(currentRecipe);
            }
        });

        // Observe favourite status and update FAB icon
        //viewModel.isFavourite().observe(this, isFavourite -> {
         //   binding.fabFavourite.setImageResource(
           //         isFavourite ? R.drawable.ic_favorite : R.drawable.ic_favorite_border
            //);
        //});
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        String recipeId = getIntent().getStringExtra("RECIPE_ID");
        if (recipeId != null) {
            viewModel.loadRecipe(recipeId);
            viewModel.getRecipe().observe(this, this::displayRecipeDetails);
        }
    }

    private void displayRecipeDetails(Recipe recipe) {
        // Update dynamic containers that can't be directly bound
        binding.ingredientsContainer.removeAllViews();
        for (List<Object> ingredient : recipe.getIngredients()) {
            TextView ingredientTextView = new TextView(this);
            String ingredientText = "• " + ingredient.get(0) + " (" + ingredient.get(2) + ")";
            ingredientTextView.setText(ingredientText);
            ingredientTextView.setTextSize(16);
            ingredientTextView.setTextAppearance(R.style.CustomEditText);
            binding.ingredientsContainer.addView(ingredientTextView);
        }

        binding.stepsContainer.removeAllViews();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            TextView stepTextView = new TextView(this);
            stepTextView.setText((i + 1) + ". " + recipe.getSteps().get(i));
            stepTextView.setTextSize(16);
            stepTextView.setTextAppearance(R.style.CustomEditText);
            binding.stepsContainer.addView(stepTextView);
        }

        binding.caloriesContainer.removeAllViews();
        TextView caloriesTextView = new TextView(this);
        caloriesTextView.setText(String.valueOf(recipe.getCalories()) + " Calories");
        caloriesTextView.setTextSize(16);
        caloriesTextView.setTextAppearance(R.style.CustomEditText);
        binding.caloriesContainer.addView(caloriesTextView);
    }
}