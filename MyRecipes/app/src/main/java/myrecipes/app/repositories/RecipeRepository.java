package myrecipes.app.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

import myrecipes.app.models.Recipe;

public class RecipeRepository {
    private final FirebaseFirestore db;
    private final CollectionReference recipesRef;
    private static RecipeRepository instance;

    private RecipeRepository() {
        db = FirebaseFirestore.getInstance();
        recipesRef = db.collection("recipes");
    }

    public static synchronized RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();

        recipesRef.addSnapshotListener((querySnapshot, e) -> {
            if (e != null) {
                System.err.println("Error listening to recipes: " + e.getMessage());
                recipesLiveData.setValue(null);
                return;
            }

            List<Recipe> recipes = querySnapshot.toObjects(Recipe.class);
            recipesLiveData.setValue(recipes);
        });

        return recipesLiveData;
    }

    public LiveData<Recipe> getRecipeById(String recipeId) {
        MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

        recipesRef.document(recipeId)
                .addSnapshotListener((documentSnapshot, e) -> {
                    if (e != null) {
                        System.err.println("Error listening to recipe: " + e.getMessage());
                        recipeLiveData.setValue(null);
                        return;
                    }

                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        Recipe recipe = documentSnapshot.toObject(Recipe.class);
                        recipeLiveData.setValue(recipe);
                    } else {
                        recipeLiveData.setValue(null);
                    }
                });

        return recipeLiveData;
    }
}