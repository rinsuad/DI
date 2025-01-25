package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    private DashboardViewModel viewModel;
    private RecipeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Logout button setup
        Button logoutButton = findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);

        // Observe recipe data
        viewModel.getRecipeLiveData().observe(this, recipes -> {
            if (adapter == null) {
                adapter = new RecipeAdapter(recipes, this);
                recyclerView.setAdapter(adapter);
            } else {
                adapter.updateRecipes(recipes);
            }
        });
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        // Navigate to DetailActivity
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("RECIPE_ID", recipe.getId());
        startActivity(intent);
    }
}