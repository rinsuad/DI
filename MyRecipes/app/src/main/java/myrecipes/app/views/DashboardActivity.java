package myrecipes.app.views;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import myrecipes.app.R;
import myrecipes.app.adapters.RecipeAdapter;
import myrecipes.app.databinding.ActivityDashboardBinding;
import myrecipes.app.viewmodels.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity {
    private DashboardViewModel recipeViewModel;
    private RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityDashboardBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        recipeAdapter = new RecipeAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(recipeAdapter);

        recipeViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        recipeViewModel.getRecipeLiveData().observe(this, recipes -> recipeAdapter.setRecipes(recipes));
    }}