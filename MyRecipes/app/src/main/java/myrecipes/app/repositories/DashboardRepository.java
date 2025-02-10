package myrecipes.app.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
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
    private final DatabaseReference favouriteRef;

    public DashboardRepository() {
        recipeRef = FirebaseDatabase.getInstance().getReference("recipes");
        favouriteRef = FirebaseDatabase.getInstance().getReference("favourites");
    }

    public void getRecipe(MutableLiveData<List<Recipe>> recipeLiveData) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("recipes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                    try {
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                        if (recipe != null) {
                            recipe.setId(recipeSnapshot.getKey());
                            recipes.add(recipe);
                        }
                    } catch (Exception e) {
                        Log.e("DashboardRepository", "Error parsing recipe: " + e.getMessage());
                    }
                }
                recipeLiveData.setValue(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DashboardRepository", "Error loading recipes: " + error.getMessage());
                recipeLiveData.postValue(new ArrayList<>());
            }
        });
    }

    // Add method to get single recipe
    public void getSingleRecipe(String recipeId, MutableLiveData<Recipe> recipeLiveData) {
        recipeRef.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Log the raw data
                Log.d("DashboardRepository", "Raw data: " + snapshot.getValue());

                // Log specific field
                Log.d("DashboardRepository", "Calories field: " +
                        snapshot.child("calorias_totales").getValue());

                Recipe recipe = snapshot.getValue(Recipe.class);
                if (recipe != null) {
                    recipe.setId(snapshot.getKey());
                    Log.d("DashboardRepository", "Parsed Recipe - Title: " + recipe.getTitle()
                            + ", Calories: " + recipe.getCalories());
                    recipeLiveData.setValue(recipe);
                } else {
                    Log.e("DashboardRepository", "Failed to parse recipe");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.e("DashboardRepository", "Error loading recipe: " + error.getMessage());
            }
        });
    }

    public void toggleFavourite(String recipeId, boolean isFavourite) {
        if (isFavourite) {
            // Get the recipe and add to favourites
            recipeRef.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Recipe recipe = snapshot.getValue(Recipe.class);
                    if (recipe != null) {
                        recipe.setId(snapshot.getKey());
                        favouriteRef.child(recipeId).setValue(recipe);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle error
                }
            });
        } else {
            // Remove from favourites
            favouriteRef.child(recipeId).removeValue();
        }
    }
}
