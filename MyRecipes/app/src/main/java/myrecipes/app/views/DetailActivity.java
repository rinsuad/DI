/*package myrecipes.app.views;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import myrecipes.app.R;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DetailViewModel;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel viewModel;
    private ImageView recipeImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView errorMessageTextView;
    private ProgressBar progressBar;
    private ViewGroup contentGroup;
    private ViewGroup errorGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeViews();
        setupViewModel();
        loadRecipeData();
    }

    private void initializeViews() {
        recipeImageView = findViewById(R.id.imageView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        contentGroup = findViewById(R.id.contentGroup);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
    }

    private void loadRecipeData() {
        String recipeId = getIntent().getStringExtra("recipe_id");
        if (recipeId == null) {
            showError("Invalid recipe ID");
            return;
        }
        viewModel.loadRecipe(recipeId);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        contentGroup.setVisibility(View.GONE);
        errorGroup.setVisibility(View.GONE);
    }

    private void showRecipe(Recipe recipe) {
        progressBar.setVisibility(View.GONE);
        contentGroup.setVisibility(View.VISIBLE);
        errorGroup.setVisibility(View.GONE);

        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());

        Glide.with(this)
                .load(recipe.getImageUrl())
                .into(recipeImageView);
    }

    private void showError(String message) {
        progressBar.setVisibility(View.GONE);
        contentGroup.setVisibility(View.GONE);
        errorGroup.setVisibility(View.VISIBLE);
        errorMessageTextView.setText(message);
    }
}*/