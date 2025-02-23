/**
 * Displays user's favorite recipes in a RecyclerView.
 * Demonstrates handling of user-specific data and empty states.
 */
package myrecipes.app.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.FragmentFavouriteBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.FavouriteViewModel;

public class FavouritesFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {
    private FavouriteViewModel viewModel;
    private FragmentFavouriteBinding binding;

    /**
     * Inflates the fragment layout using View Binding.
     * Shows proper fragment initialization with view binding.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Sets up ViewModel and UI components after view creation.
     * Demonstrates proper lifecycle-aware setup and data observation.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Setup RecyclerView with LinearLayoutManager
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Observe favorite recipes data
        viewModel.getRecipeLiveData().observe(getViewLifecycleOwner(), recipes -> {
            if (binding.recyclerView.getAdapter() == null) {
                // Create new adapter if none exists
                RecipeAdapter adapter = new RecipeAdapter(recipes, this);
                binding.recyclerView.setAdapter(adapter);
            } else {
                // Update existing adapter with new data
                ((RecipeAdapter) binding.recyclerView.getAdapter()).updateRecipes(recipes);
            }

            // Handle empty state visibility
            binding.emptyStateText.setVisibility(recipes.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    /**
     * Handles recipe click events from the RecyclerView.
     * Shows navigation with arguments using Navigation component.
     */
    @Override
    public void onRecipeClick(Recipe recipe) {
        Bundle args = new Bundle();
        args.putString("RECIPE_ID", recipe.getId());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_favourites_to_detail, args);
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