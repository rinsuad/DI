package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.ActivityDashboardBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    private DashboardViewModel viewModel;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // RecyclerView setup
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Logout button setup
        binding.logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // Observe recipe data
        viewModel.getRecipeLiveData().observe(this, recipes -> {
            if (binding.recyclerView.getAdapter() == null) {
                RecipeAdapter adapter = new RecipeAdapter(recipes, this);
                binding.recyclerView.setAdapter(adapter);
            } else {
                ((RecipeAdapter) binding.recyclerView.getAdapter()).updateRecipes(recipes);
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("RECIPE_ID", recipe.getId());
        startActivity(intent);
    }
}