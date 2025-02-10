package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;

import com.google.firebase.auth.FirebaseAuth;

import myrecipes.app.R;
import myrecipes.app.databinding.ActivityMainBinding;
import myrecipes.app.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
            updateToolbarTitle(destination);
        });

        // Observe loading state
        viewModel.getLoadingState().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Handle navigation item selection
        binding.navigationView.setNavigationItemSelectedListener(this::handleNavigationItemSelected);
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
        // Sign out from Firebase
        FirebaseAuth.getInstance().signOut();

        // Clear any relevant SharedPreferences
        getSharedPreferences("settings", MODE_PRIVATE)
                .edit()
                .clear()
                .apply();

        // Navigate to Login Activity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void updateToolbarTitle(NavDestination destination) {
        String title;
        int destId = destination.getId();

        if (destId == R.id.dashboardFragment) {
            title = "Dashboard";
        } else if (destId == R.id.favouritesFragment) {
            title = "Favorites";
        } else if (destId == R.id.profileFragment) {
            title = "Profile";
        } else if (destId == R.id.detailFragment) {
            title = "Recipe Details";
        } else {
            title = getString(R.string.app_name);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}