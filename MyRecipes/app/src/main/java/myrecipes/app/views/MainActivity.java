/**
 * Main container activity that serves as the host for the app's main features.
 * Implements navigation drawer pattern and manages global app state.
 * Key responsibilities:
 * - Navigation management using Navigation component
 * - Dark mode theme handling
 * - User session management
 * - Global UI state coordination
 */
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
    // View binding instance for type-safe view access
    private ActivityMainBinding binding;

    // ViewModel for managing UI-related data
    private MainViewModel viewModel;

    // Navigation controller for managing fragment navigation
    private NavController navController;

    // Configuration for top-level destinations
    private AppBarConfiguration appBarConfiguration;

    // Constants for SharedPreferences
    private static final String PREFS_NAME = "AppSettings";
    private static final String DARK_MODE_KEY = "dark_mode_enabled";

    /**
     * Initializes the activity and sets up all necessary components.
     * Called when the activity is first created.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply saved theme before super.onCreate() to prevent theme flickering
        applyDarkMode();

        super.onCreate(savedInstanceState);

        // Initialize view binding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup toolbar as action bar
        setSupportActionBar(binding.toolbar);

        // Initialize ViewModel using ViewModelProvider
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        // Setup data binding with ViewModel
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        // Setup navigation components
        setupNavigation();

        // Setup observers for data changes
        observeViewModel();
    }

    /**
     * Sets up the Navigation component with DrawerLayout integration.
     * Configures the navigation graph, toolbar, and drawer layout.
     */
    private void setupNavigation() {
        // Get NavController from NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        // Define top-level destinations for the drawer
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.dashboardFragment,
                R.id.favouritesFragment,
                R.id.profileFragment
        ).setOpenableLayout(binding.drawerLayout).build();

        // Connect NavigationUI with toolbar and navigation drawer
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        // Add destination change listener to update toolbar title
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            String title = "";
            int id = destination.getId();

            // Set appropriate title based on destination
            if (id == R.id.dashboardFragment) {
                title = "Mis Recetas";
            } else if (id == R.id.favouritesFragment) {
                title = "Favoritos";
            } else if (id == R.id.profileFragment) {
                title = "Perfil";
            } else if (id == R.id.detailFragment) {
                title = "Detalles de Receta";
            }

            // Update toolbar title
            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(title);
            }
        });

        // Setup navigation item selection handling
        binding.navigationView.setNavigationItemSelectedListener(this::handleNavigationItemSelected);
    }

    /**
     * Observes ViewModel's LiveData for UI updates.
     * Sets up observers for loading state changes.
     */
    private void observeViewModel() {
        viewModel.getLoadingState().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });
    }

    /**
     * Handles navigation item selection from the drawer menu.
     * Manages both navigation and special actions like logout.
     *
     * @param item The selected menu item
     * @return true if the item was handled, false otherwise
     */
    private boolean handleNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();
            return true;
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return NavigationUI.onNavDestinationSelected(item, navController);
    }

    /**
     * Handles user logout process.
     * Clears authentication state and user preferences.
     */
    private void logout() {
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Clear app preferences
        getSharedPreferences("settings", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // Navigate to login screen
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Handles the Up button press in the action bar.
     * Part of the NavigationUI integration.
     */
    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    /**
     * Applies the saved dark mode preference.
     * Called before onCreate to prevent theme flickering.
     */
    private void applyDarkMode() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isDarkMode = prefs.getBoolean(DARK_MODE_KEY, false);
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    /**
     * Public method to toggle dark mode theme.
     * Can be called from fragments to change the app theme.
     *
     * @param enableDarkMode true to enable dark mode, false to disable
     */
    public void toggleDarkMode(boolean enableDarkMode) {
        // Save preference
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putBoolean(DARK_MODE_KEY, enableDarkMode).apply();

        // Apply theme
        AppCompatDelegate.setDefaultNightMode(
                enableDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }
}