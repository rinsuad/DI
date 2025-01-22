package myrecipes.app.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import myrecipes.app.R;
import myrecipes.app.databinding.ItemRecipeBinding;
import myrecipes.app.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRecipeBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_recipe,
                parent,
                false
        );
        return new RecipeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        return recipes != null ? recipes.size() : 0;
    }

    static class RecipeViewHolder extends RecyclerView.ViewHolder {
        private final ItemRecipeBinding binding;

        public RecipeViewHolder(@NonNull ItemRecipeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Recipe recipe) {
            binding.setRecipe(recipe);
            binding.executePendingBindings();
        }
    }
}