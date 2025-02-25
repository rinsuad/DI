/**
 * Manages user favorites functionality, including adding/removing favorites
 * and retrieving a user's favorite recipes.
 */
package myrecipes.app.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;
import myrecipes.app.models.Recipe;

public class FavouriteRepository {
    private final DatabaseReference favouriteRef;
    private final FirebaseAuth auth;

    /**
     * Constructor initializes Firebase Authentication and Database references.
     * The userFavorites node stores the favorite status for each user-recipe combination.
     */
    public FavouriteRepository() {
        auth = FirebaseAuth.getInstance();
        favouriteRef = FirebaseDatabase.getInstance().getReference("userFavorites");
    }

    /**
     * Retrieves all favorite recipes for the current user.
     * This is a two-step process:
     * 1. Get all favorite recipe IDs for the user
     * 2. Fetch the actual recipe data for each ID
     *
     * @param recipeLiveData LiveData object to be updated with the list of favorite recipes
     */
    public void getFavourites(MutableLiveData<List<Recipe>> recipeLiveData) {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userFavoritesRef = favouriteRef.child(userId);
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");

        userFavoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Step 1: Collect all favorite recipe IDs
                List<String> favoriteIds = new ArrayList<>();
                for (DataSnapshot favoriteSnapshot : snapshot.getChildren()) {
                    if (Boolean.TRUE.equals(favoriteSnapshot.getValue(Boolean.class))) {
                        favoriteIds.add(favoriteSnapshot.getKey());
                    }
                }

                // Step 2: Fetch recipe data for each favorite ID
                recipesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot recipesSnapshot) {
                        List<Recipe> recipes = new ArrayList<>();
                        for (String favoriteId : favoriteIds) {
                            DataSnapshot recipeSnapshot = recipesSnapshot.child(favoriteId);
                            Recipe recipe = recipeSnapshot.getValue(Recipe.class);
                            if (recipe != null) {
                                recipe.setId(favoriteId);
                                recipes.add(recipe);
                            }
                        }
                        recipeLiveData.setValue(recipes);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });
    }

    /**
     * Toggles the favorite status of a recipe for the current user.
     *
     * @param recipeId ID of the recipe to toggle
     * @param isFavorite true to add to favorites, false to remove
     */
    public void toggleFavorite(String recipeId, boolean isFavorite) {
        String userId = auth.getCurrentUser().getUid();
        if (isFavorite) {
            // Add to favorites by setting value to true
            favouriteRef.child(userId).child(recipeId).setValue(true);
        } else {
            // Remove from favorites by removing the node
            favouriteRef.child(userId).child(recipeId).removeValue();
        }
    }

    /**
     * Checks if a recipe is in the user's favorites.
     * Uses a ValueEventListener to stay updated with changes.
     *
     * @param recipeId ID of the recipe to check
     * @param isFavoriteLiveData LiveData object to be updated with the favorite status
     */
    public void checkIsFavorite(String recipeId, MutableLiveData<Boolean> isFavoriteLiveData) {
        String userId = auth.getCurrentUser().getUid();
        favouriteRef.child(userId).child(recipeId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // snapshot.exists() returns true if the recipe is in favorites
                        isFavoriteLiveData.setValue(snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
    }
}
