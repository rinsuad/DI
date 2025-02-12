package myrecipes.app.views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;

import com.google.firebase.auth.FirebaseAuth;

import myrecipes.app.R;
import myrecipes.app.databinding.ActivityMainBinding;
import myrecipes.app.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;
    private static final String PREFS_NAME = "AppSettings";
    private static final String DARK_MODE_KEY = "dark_mode_enabled";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applyDarkMode();

        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup Toolbar
        setSupportActionBar(binding.toolbar);

        // Setup ViewModel
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Setup Navigation
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Setup DrawerLayout with Navigation
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboardFragment,
                R.id.favouritesFragment,
                R.id.profileFragment
        ).setOpenableLayout(binding.drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        // Add destination change listener to update toolbar title
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title = "";
            int id = destination.getId();

            if (id == R.id.dashboardFragment) {
                title = "Mis Recetas";
            } else if (id == R.id.favouritesFragment) {
                title = "Favoritos";
            } else if (id == R.id.profileFragment) {
                title = "Perfil";
            } else if (id == R.id.detailFragment) {
                title = "Detalles de Receta";
            }

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        });

        // Handle navigation item selection
        binding.navigationView.setNavigationItemSelectedListener(this::handleNavigationItemSelected);

        // Observe loading state
        viewModel.getLoadingState().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    private boolean handleNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return NavigationUI.onNavDestinationSelected(item, navController);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        getSharedPreferences("settings", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void applyDarkMode() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean(DARK_MODE_KEY, false);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    // Create a public method to toggle dark mode that can be called from fragments
    public void toggleDarkMode(boolean enableDarkMode) {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(DARK_MODE_KEY, enableDarkMode).apply();

        AppCompatDelegate.setDefaultNightMode(
                enableDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }
}