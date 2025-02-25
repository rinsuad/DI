/**
 * Displays detailed information about a specific recipe.
 * Demonstrates dynamic UI creation and data binding with ViewModel.
 * Shows how to handle complex UI updates with LiveData observation.
 */
package myrecipes.app.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import java.util.List;
import myrecipes.app.R;
import myrecipes.app.databinding.FragmentDetailBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DetailViewModel;

public class DetailFragment extends Fragment {
    private DetailViewModel viewModel;
    private FragmentDetailBinding binding;
    private String recipeId;

    /**
     * Retrieves recipe ID from navigation arguments.
     * Shows how to handle fragment arguments safely.
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeId = getArguments() != null ? getArguments().getString("RECIPE_ID") : null;
    }

    /**
     * Inflates the fragment layout using View Binding.
     * Demonstrates proper view binding setup in fragments.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Sets up ViewModel and UI components after view creation.
     * Shows proper lifecycle-aware component initialization.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupFavouriteFab();
    }

    /**
     * Initializes ViewModel and sets up data observation.
     * Demonstrates MVVM pattern implementation with LiveData.
     */
    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        if (recipeId != null) {
            viewModel.loadRecipe(recipeId);
            viewModel.getRecipe().observe(getViewLifecycleOwner(), this::displayRecipeDetails);
        }
    }

    /**
     * Sets up favorite button functionality and state observation.
     * Shows how to handle user interactions and state updates.
     */
    private void setupFavouriteFab() {
        binding.fabFavourite.setOnClickListener(v -> {
            Recipe currentRecipe = viewModel.getRecipe().getValue();
            if (currentRecipe != null) {
                viewModel.toggleFavourite(currentRecipe);
            }
        });

        viewModel.isFavourite().observe(getViewLifecycleOwner(), isFavourite -> {
            binding.fabFavourite.setImageResource(
                    isFavourite ? R.drawable.ic_favourite : R.drawable.ic_favourite_border
            );
        });
    }

    /**
     * Displays recipe details in the UI.
     * Demonstrates dynamic view creation and complex UI updates.
     *
     * @param recipe The recipe object containing all details to display
     */
    private void displayRecipeDetails(Recipe recipe) {
        // Load recipe image using Glide
        Glide.with(requireContext())
                .load(recipe.getImageUrl())
                .into(binding.recipeImageView);

        // Display ingredients dynamically
        binding.ingredientsContainer.removeAllViews();
        for (List<Object> ingredient : recipe.getIngredients()) {
            TextView ingredientTextView = new TextView(requireContext());
            String ingredientText = "• " + ingredient.get(0) + " (" + ingredient.get(2) + ")";
            ingredientTextView.setText(ingredientText);
            ingredientTextView.setTextSize(18);
            ingredientTextView.setTextAppearance(R.style.CustomEditText);
            binding.ingredientsContainer.addView(ingredientTextView);
        }

        // Display cooking steps
        binding.stepsContainer.removeAllViews();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            TextView stepTextView = new TextView(requireContext());
            stepTextView.setText((i + 1) + ". " + recipe.getSteps().get(i));
            stepTextView.setTextSize(18);
            stepTextView.setTextAppearance(R.style.CustomEditText);
            binding.stepsContainer.addView(stepTextView);
        }

        // Display calorie information
        binding.caloriesContainer.removeAllViews();
        TextView caloriesTextView = new TextView(requireContext());
        int calories = recipe.getCalories();
        caloriesTextView.setText(calories + " Calorias totales");
        caloriesTextView.setTextSize(18);
        caloriesTextView.setTextAppearance(R.style.CustomEditText);
        binding.caloriesContainer.addView(caloriesTextView);
    }

    /**
     * Cleanup to prevent memory leaks.
     * Shows proper handling of view binding cleanup.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}