package myrecipes.app.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.ActivityDashboardBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    private DashboardViewModel viewModel;
    private ActivityDashboardBinding binding;
    private static final String PREFS_NAME = "settings";
    private static final String NIGHT_MODE_KEY = "night_mode";
    private boolean isNightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        // Set up toolbar
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        // ViewModel setup
        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
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

        // Load saved night mode preference
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        isNightMode = prefs.getBoolean(NIGHT_MODE_KEY, false);
        AppCompatDelegate.setDefaultNightMode(isNightMode ?
                AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);

    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("RECIPE_ID", recipe.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu, menu);

        // Toggle visibility of the correct night mode icon
        MenuItem lightModeItem = menu.findItem(R.id.action_light_mode);
        MenuItem nightModeItem = menu.findItem(R.id.action_night_mode);

        if (isNightMode) {
            nightModeItem.setVisible(false);
            lightModeItem.setVisible(true);
        } else {
            nightModeItem.setVisible(true);
            lightModeItem.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        int itemId = item.getItemId();

        if (itemId == R.id.action_light_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor.putBoolean(NIGHT_MODE_KEY, false);
            editor.apply();
            recreate();
            return true;
        } else if (itemId == R.id.action_night_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor.putBoolean(NIGHT_MODE_KEY, true);
            editor.apply();
            recreate();
            return true;
        } else if (itemId == R.id.action_favourites) {
            Intent intent = new Intent(DashboardActivity.this, FavouriteActivity.class);
            startActivity(intent);
            return true;
        } else if (itemId == R.id.action_logout) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}