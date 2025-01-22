package myrecipes.app.repositories;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.Executors;
import myrecipes.app.models.Recipe;

public class RecipeRepository {
    private final DatabaseReference database;
    private static RecipeRepository instance;

    public RecipeRepository() {
        this.database = FirebaseDatabase.getInstance().getReference();
    }

    // Public static method to get the single instance
    public static RecipeRepository getInstance() {
        if (instance == null) {
            synchronized (RecipeRepository.class) {
                if (instance == null) {
                    instance = new RecipeRepository();
                }
            }
        }
        return instance;
    }

    public Task<Recipe> getRecipe(String recipeId) {
        return Tasks.call(Executors.newSingleThreadExecutor(), () -> {
            DataSnapshot dataSnapshot = Tasks.await(
                    database.child("recipes").child(recipeId).get()
            );

            if (!dataSnapshot.exists()) {
                throw new Exception("Recipe not found");
            }

            return new Recipe(
                    dataSnapshot.child("title").getValue(String.class),
                    dataSnapshot.child("description").getValue(String.class),
                    dataSnapshot.child("imageUrl").getValue(String.class)
            );
        });
    }
}
