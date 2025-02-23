/**
 * This class handles all data operations related to recipes in the Firebase Realtime Database.
 * It acts as a single source of truth for recipe data in the application.
 */
package myrecipes.app.repositories;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;
import myrecipes.app.models.Recipe;

public class DashboardRepository {
    // Database references for different nodes in Firebase
    private final DatabaseReference recipeRef;
    private final DatabaseReference favouriteRef;

    /**
     * Constructor initializes Firebase database references.
     * These references point to specific nodes in the Firebase Realtime Database:
     * - recipes: stores all recipe data
     * - favourites: stores user favorite relationships
     */
    public DashboardRepository() {
        recipeRef = FirebaseDatabase.getInstance().getReference("recipes");
        favouriteRef = FirebaseDatabase.getInstance().getReference("favourites");
    }

    /**
     * Retrieves all recipes from Firebase and updates the provided LiveData object.
     * Uses Firebase's ValueEventListener for one-time data fetch.
     *
     * @param recipeLiveData LiveData object that will be updated with the recipe list
     */
    public void getRecipe(MutableLiveData<List<Recipe>> recipeLiveData) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("recipes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Recipe> recipes = new ArrayList<>();
                // Iterate through all recipes in the snapshot
                for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                    try {
                        // Convert Firebase data to Recipe object
                        Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                        if (recipe != null) {
                            // Set the Firebase key as recipe ID
                            recipe.setId(recipeSnapshot.getKey());
                            recipes.add(recipe);
                        }
                    } catch (Exception e) {
                        Log.e("DashboardRepository", "Error parsing recipe: " + e.getMessage());
                    }
                }
                // Update LiveData with the new list
                recipeLiveData.setValue(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DashboardRepository", "Error loading recipes: " + error.getMessage());
                // Return empty list in case of error
                recipeLiveData.postValue(new ArrayList<>());
            }
        });
    }

    /**
     * Retrieves a single recipe by its ID.
     * Includes detailed logging for debugging purposes.
     *
     * @param recipeId ID of the recipe to retrieve
     * @param recipeLiveData LiveData object that will be updated with the single recipe
     */
    public void getSingleRecipe(String recipeId, MutableLiveData<Recipe> recipeLiveData) {
        recipeRef.child(recipeId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // Debug logging of raw data
                Log.d("DashboardRepository", "Raw data: " + snapshot.getValue());
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
}