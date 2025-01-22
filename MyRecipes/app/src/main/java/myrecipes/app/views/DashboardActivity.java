package myrecipes.app.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import myrecipes.app.databinding.ActivityDashboardBinding;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DetailViewModel;


public class DashboardActivity extends AppCompatActivity {
    private ActivityDashboardBinding binding;
    private DetailViewModel viewModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        setupObservers();
        setupListeners();

        viewModel.loadRecipe("avena_kinder_bueno");
    }

    private void setupObservers() {
        viewModel.getRecipe().observe(this, this::updateUI);

        viewModel.getError().observe(this, errorMessage ->
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        );

        viewModel.getLoading().observe(this, isLoading -> {
            // Implementar loading si es necesario
        });
    }

    private void setupListeners() {
        binding.logoutButton.setOnClickListener(v -> logout());
    }

    private void updateUI(Recipe recipe) {
        binding.titleTextView.setText(recipe.getTitle());
        binding.descriptionTextView.setText(recipe.getDescription());

        Glide.with(this)
                .load(recipe.getImageUrl())
                .into(binding.imageView);
    }

    private void logout() {
        mAuth.signOut();
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}