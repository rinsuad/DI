/**
 * Main fragment for displaying recipe list.
 * Demonstrates MVVM pattern with RecyclerView and data binding.
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
import myrecipes.app.databinding.FragmentDashboardBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DashboardViewModel;

public class DashboardFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {
    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;

    /**
     * Inflates the fragment layout using View Binding.
     * Shows proper fragment initialization pattern.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Sets up ViewModel and RecyclerView after view creation.
     * Demonstrates proper lifecycle-aware setup of UI components.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // Setup RecyclerView
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Observe recipe data changes
        observeRecipeData();
    }

    /**
     * Observes recipe data changes and updates UI accordingly.
     * Shows how to handle RecyclerView adapter updates with LiveData.
     */
    private void observeRecipeData() {
        viewModel.getRecipeLiveData().observe(getViewLifecycleOwner(), recipes -> {
            if (binding.recyclerView.getAdapter() == null) {
                // Create new adapter if none exists
                RecipeAdapter adapter = new RecipeAdapter(recipes, this);
                binding.recyclerView.setAdapter(adapter);
            } else {
                // Update existing adapter
                ((RecipeAdapter) binding.recyclerView.getAdapter()).updateRecipes(recipes);
            }
        });
    }

    /**
     * Handles recipe click events from the RecyclerView.
     * Demonstrates navigation with arguments using Navigation component.
     */
    @Override
    public void onRecipeClick(Recipe recipe) {
        Bundle args = new Bundle();
        args.putString("RECIPE_ID", recipe.getId());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_dashboard_to_detail, args);
    }

    /**
     * Cleanup to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}