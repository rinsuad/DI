package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.FragmentFavouriteBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.FavouriteViewModel;

public class FavouritesFragment extends Fragment implements RecipeAdapter.OnRecipeClickListener {
    private FavouriteViewModel viewModel;
    private FragmentFavouriteBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavouriteBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
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

        // Toolbar setup
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(binding.toolbar);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            binding.toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
        }
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        // Navigate using your navigation component or start activity
        Intent intent = new Intent(requireContext(), DetailFragment.class);
        intent.putExtra("RECIPE_ID", recipe.getId());
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}