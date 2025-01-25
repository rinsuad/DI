package myrecipes.app.views;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import myrecipes.app.R;
import myrecipes.app.models.Recipe;
import myrecipes.app.viewmodels.DetailViewModel;

public class DetailActivity extends AppCompatActivity {
    private DetailViewModel viewModel;
    private ImageView imageView;
    private TextView titleTextView, descriptionTextView;
    private LinearLayout ingredientsContainer, stepsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initializeViews();
        setupViewModel();
    }

    private void initializeViews() {
        imageView = findViewById(R.id.imageView);
        titleTextView = findViewById(R.id.titleTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        ingredientsContainer = findViewById(R.id.ingredientsContainer);
        stepsContainer = findViewById(R.id.stepsContainer);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);

        String recipeId = getIntent().getStringExtra("RECIPE_ID");
        if (recipeId != null) {
            viewModel.loadRecipe(recipeId);

            viewModel.getRecipe().observe(this, this::displayRecipeDetails);
        }
    }

    private void displayRecipeDetails(Recipe recipe) {
        titleTextView.setText(recipe.getTitle());
        descriptionTextView.setText(recipe.getDescription());

        Glide.with(this)
                .load(recipe.getImageUrl())
                .into(imageView);

        //  need to extend this to handle ingredients and steps
    }
}