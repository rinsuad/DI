package myrecipes.app.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import myrecipes.app.models.Recipe;

public class FavouriteRepository {
    private final DatabaseReference favouriteRef;
    private final FirebaseAuth auth;

    public FavouriteRepository() {
        auth = FirebaseAuth.getInstance();
        favouriteRef = FirebaseDatabase.getInstance().getReference("userFavorites");
    }

    public void getFavourites(MutableLiveData<List<Recipe>> recipeLiveData) {
        String userId = auth.getCurrentUser().getUid();
        DatabaseReference userFavoritesRef = favouriteRef.child(userId);
        DatabaseReference recipesRef = FirebaseDatabase.getInstance().getReference("recipes");

        userFavoritesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> favoriteIds = new ArrayList<>();
                for (DataSnapshot favoriteSnapshot : snapshot.getChildren()) {
                    if (Boolean.TRUE.equals(favoriteSnapshot.getValue(Boolean.class))) {
                        favoriteIds.add(favoriteSnapshot.getKey());
                    }
                }

                // Get the actual recipes
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

    public void toggleFavorite(String recipeId, boolean isFavorite) {
        String userId = auth.getCurrentUser().getUid();
        if (isFavorite) {
            // Add to favorites
            favouriteRef.child(userId).child(recipeId).setValue(true);
        } else {
            // Remove from favorites
            favouriteRef.child(userId).child(recipeId).removeValue();
        }
    }

    public void checkIsFavorite(String recipeId, MutableLiveData<Boolean> isFavoriteLiveData) {
        String userId = auth.getCurrentUser().getUid();
        favouriteRef.child(userId).child(recipeId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // Will be null if the recipe is not in favorites
                        isFavoriteLiveData.setValue(snapshot.exists());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
    }
}