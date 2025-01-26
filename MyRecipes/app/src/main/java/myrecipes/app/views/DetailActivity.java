package myrecipes.app.views;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

import myrecipes.app.R;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DetailViewModel;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel viewModel;
    private ShapeableImageView imageView;
    private TextView titleTextView, descriptionTextView, caloriesTextView;
    private LinearLayout ingredientsContainer, stepsContainer;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeViews();
        setupToolbar();
        setupViewModel();
    }

    private void initializeViews() {
        imageView = findViewById(R.id.imageView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        ingredientsContainer = findViewById(R.id.ingredientsContainer);
        stepsContainer = findViewById(R.id.stepsContainer);
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbarLayout);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        String recipeId = getIntent().getStringExtra("RECIPE_ID");
        if (recipeId != null) {
            viewModel.loadRecipe(recipeId);
            viewModel.getRecipe().observe(this, this::displayRecipeDetails);
        }
    }

    private void displayRecipeDetails(Recipe recipe) {
        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());
        collapsingToolbarLayout.setTitle(recipe.getTitle());

        Glide.with(this)
                .load(recipe.getImageUrl())
                .into(imageView);

        // Display Ingredients
        ingredientsContainer.removeAllViews();
        for (List<Object> ingredient : recipe.getIngredients()) {
            TextView ingredientTextView = new TextView(this);

            // Assuming format is [name, calories, quantity]
            String ingredientText = "• " + ingredient.get(0) +
                    " (" + ingredient.get(2) + ")";
            ingredientTextView.setText(ingredientText);

            ingredientTextView.setTextSize(16);
            ingredientsContainer.addView(ingredientTextView);
        }

        // Display Steps
        stepsContainer.removeAllViews();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            TextView stepTextView = new TextView(this);
            stepTextView.setText((i + 1) + ". " + recipe.getSteps().get(i)); // Numbered steps
            stepTextView.setTextSize(16);
            stepsContainer.addView(stepTextView);
        }

        // Display Calories
        LinearLayout caloriesContainer = findViewById(R.id.caloriesContainer);
        caloriesContainer.removeAllViews();
        TextView caloriesTextView = new TextView(this);
        caloriesTextView.setText(String.valueOf(recipe.getCalories()) + " Calories");
        caloriesTextView.setTextSize(16);
        caloriesContainer.addView(caloriesTextView);
    }
}