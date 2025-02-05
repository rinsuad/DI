package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import myrecipes.app.R;
import myrecipes.app.databinding.ActivityMainBinding;
import myrecipes.app.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupNavigationView();
        setupObservers();

        if (savedInstanceState == null) {
            openFragment(new DashboardFragment());
        }
    }

    private void setupNavigationView() {
        binding.navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_dashboard) {
                openFragment(new DashboardFragment());
            } else if (id == R.id.nav_favourites) {
                openFragment(new FavouritesFragment());
            } else if (id == R.id.nav_profile) {
                openFragment(new ProfileFragment());
            } else if (id == R.id.nav_logout) {
                logoutUser();
            }
            binding.drawerLayout.closeDrawers();
            return true;
        });
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }

    private void setupObservers() {
        viewModel.getLoadingState().observe(this, isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                showError(error);
                viewModel.clearError();
            }
        });
    }

    private void logoutUser() {
        viewModel.setLoading(true);
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showError(String error) {
        Snackbar.make(binding.fragmentContainer, error, Snackbar.LENGTH_LONG).show();
    }
}