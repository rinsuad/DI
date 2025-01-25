package myrecipes.app.repositories;

import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import myrecipes.app.models.Recipe;

public class DashboardRepository {
    private final DatabaseReference recipeRef;

    public DashboardRepository() {
        recipeRef = FirebaseDatabase.getInstance().getReference("recipes");
    }

    public void getRecipe(MutableLiveData<List<Recipe>> recipeLiveData) {
        recipeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Recipe recipe = child.getValue(Recipe.class);
                    if (recipe != null) {
                        recipe.setId(child.getKey());  // Set the Firebase key as the recipe ID
                        recipes.add(recipe);
                    }
                }
                recipeLiveData.setValue(recipes);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Manejo de errores
            }
        });
    }

    // Add method to get single recipe
    public void getSingleRecipe(String recipeId, MutableLiveData<Recipe> recipeLiveData) {
        recipeRef.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Recipe recipe = snapshot.getValue(Recipe.class);
                if (recipe != null) {
                    recipe.setId(snapshot.getKey());
                    recipeLiveData.setValue(recipe);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Error handling
            }
        });
    }
}
