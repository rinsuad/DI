package myrecipes.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import myrecipes.app.R;
import myrecipes.app.models.Recipe;
import java.util.List;

/**
 * This adapter class handles the display of Recipe items in a RecyclerView.
 * It follows the Adapter pattern to efficiently recycle and reuse views as the user scrolls.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    // List of recipes to display
    private List<Recipe> recipes;
    // Interface for handling recipe click events
    private OnRecipeClickListener clickListener;

    /**
     * Interface definition for recipe click callbacks.
     * Activities/Fragments can implement this to handle recipe selection.
     */
    public interface OnRecipeClickListener {
        void onRecipeClick(Recipe recipe);
    }

    /**
     * Constructor for the adapter.
     *
     * @param recipes Initial list of recipes to display
     * @param listener Callback for recipe click events
     */
    public RecipeAdapter(List<Recipe> recipes, OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.clickListener = listener;
    }

    /**
     * Creates new ViewHolder instances when needed by the RecyclerView.
     * This is called when RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added
     * @param viewType The view type of the new View (not used in this implementation)
     * @return A new ViewHolder that holds a View for a recipe item
     */
    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for a recipe item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);
        return new RecipeViewHolder(view);
    }

    /**
     * Updates the contents of a ViewHolder to reflect the recipe at the given position.
     * This is called by RecyclerView to display the data at the specified position.
     *
     * @param holder The ViewHolder to update
     * @param position The position of the item in the data set
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        // Set the recipe title
        holder.titleTextView.setText(recipe.getTitle());

        // Set up click listener for the entire item view
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onRecipeClick(recipe);
            }
        });
    }

    /**
     * Returns the total number of items in the data set.
     *
     * @return The total number of recipes
     */
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    /**
     * ViewHolder class for recipe items.
     * Caches references to views to avoid repeated findViewById calls.
     */
    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;

        RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            // Cache references to views
            titleTextView = itemView.findViewById(R.id.recipeTitleTextView);
        }
    }

    /**
     * Updates the recipe list and refreshes the display.
     * Note: In a production app, you might want to use DiffUtil for more efficient updates.
     *
     * @param newRecipes New list of recipes to display
     */
    public void updateRecipes(List<Recipe> newRecipes) {
        this.recipes = newRecipes;
        notifyDataSetChanged();
    }
}