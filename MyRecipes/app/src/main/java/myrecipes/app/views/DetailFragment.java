package myrecipes.app.views;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recipeId = getArguments() != null ? getArguments().getString("RECIPE_ID") : null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViewModel();
        setupFavouriteFab();
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        if (recipeId != null) {
            viewModel.loadRecipe(recipeId);
            viewModel.getRecipe().observe(getViewLifecycleOwner(), this::displayRecipeDetails);
        }
    }

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

    private void displayRecipeDetails(Recipe recipe) {
        // Set recipe image
        Glide.with(requireContext())
                .load(recipe.getImageUrl())
                .into(binding.recipeImageView);

        binding.ingredientsContainer.removeAllViews();
        for (List<Object> ingredient : recipe.getIngredients()) {
            TextView ingredientTextView = new TextView(requireContext());
            String ingredientText = "• " + ingredient.get(0) + " (" + ingredient.get(2) + ")";
            ingredientTextView.setText(ingredientText);
            ingredientTextView.setTextSize(18);
            ingredientTextView.setTextAppearance(R.style.CustomEditText);
            binding.ingredientsContainer.addView(ingredientTextView);
        }

        binding.stepsContainer.removeAllViews();
        for (int i = 0; i < recipe.getSteps().size(); i++) {
            TextView stepTextView = new TextView(requireContext());
            stepTextView.setText((i + 1) + ". " + recipe.getSteps().get(i));
            stepTextView.setTextSize(18);
            stepTextView.setTextAppearance(R.style.CustomEditText);
            binding.stepsContainer.addView(stepTextView);
        }

        binding.caloriesContainer.removeAllViews();
        TextView caloriesTextView = new TextView(requireContext());
        int calories = recipe.getCalories();
        caloriesTextView.setText(calories + " Calorias totales");
        caloriesTextView.setTextSize(18);
        caloriesTextView.setTextAppearance(R.style.CustomEditText);
        binding.caloriesContainer.addView(caloriesTextView);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}