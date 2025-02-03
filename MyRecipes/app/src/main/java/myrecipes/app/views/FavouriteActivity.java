package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.ActivityFavouriteBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.FavouriteViewModel;

public class FavouriteActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    private FavouriteViewModel viewModel;
    private ActivityFavouriteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favourite);

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // RecyclerView setup
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Observe recipe data
        viewModel.getRecipeLiveData().observe(this, recipes -> {
            if (binding.recyclerView.getAdapter() == null) {
                RecipeAdapter adapter = new RecipeAdapter(recipes, this);
                binding.recyclerView.setAdapter(adapter);
            } else {
                ((RecipeAdapter) binding.recyclerView.getAdapter()).updateRecipes(recipes);
            }
        });

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("RECIPE_ID", recipe.getId());
        startActivity(intent);
    }
}
