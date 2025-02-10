package myrecipes.app.views;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.FragmentDashboardBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DashboardViewModel;

import static android.content.Context.MODE_PRIVATE;

public class DashboardFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {
    private DashboardViewModel viewModel;
    private FragmentDashboardBinding binding;
    private static final String PREFS_NAME = "settings";
    private static final String NIGHT_MODE_KEY = "night_mode";
    private boolean isNightMode;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        // RecyclerView setup
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // Observe recipe data
        viewModel.getRecipeLiveData().observe(getViewLifecycleOwner(), recipes -> {
            if (binding.recyclerView.getAdapter() == null) {
                RecipeAdapter adapter = new RecipeAdapter(recipes, this);
                binding.recyclerView.setAdapter(adapter);
            } else {
                ((RecipeAdapter) binding.recyclerView.getAdapter()).updateRecipes(recipes);
            }
        });

        // Load saved night mode preference
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isNightMode = prefs.getBoolean(NIGHT_MODE_KEY, false);
        AppCompatDelegate.setDefaultNightMode(isNightMode ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Bundle args = new Bundle();
        args.putString("RECIPE_ID", recipe.getId());
        Navigation.findNavController(requireView())
                .navigate(R.id.action_dashboard_to_detail, args);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}