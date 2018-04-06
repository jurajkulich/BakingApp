package com.example.juraj.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.juraj.bakingapp.R;
import com.example.juraj.bakingapp.RecipeDetailActivity;
import com.example.juraj.bakingapp.data.model.Recipe;

import java.util.List;

/**
 * Created by Juraj on 3/19/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    public static final String recipeBundle = "RECIPE";

    private List<Recipe> mRecipeList;
    private Context mContext;

    public RecipesAdapter(List<Recipe> recipeList, Context context) {
        mRecipeList = recipeList;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mRecipeNameTextView;
        public TextView mRecipeServingsTextview;
        public ImageView mRecipeImageImageView;

        public ViewHolder(View view) {
            super(view);
            mRecipeNameTextView = view.findViewById(R.id.item_recipe_name);
            mRecipeServingsTextview = view.findViewById(R.id.item_recipe_servings);
            mRecipeImageImageView = view.findViewById(R.id.tem_recipe_image_placeholder);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_recipe, parent, false );

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Recipe recipe = mRecipeList.get(position);

        TextView recipeNameTextView = holder.mRecipeNameTextView;
        recipeNameTextView.setText(recipe.getName());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RecipeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(recipeBundle, recipe);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

        TextView recipeServingsTextView = holder.mRecipeServingsTextview;
        recipeServingsTextView.setText(mContext.getResources().getString(R.string.servings) + recipe.getServings());

        ImageView imageView = holder.mRecipeImageImageView;
        imageView.setImageResource(R.drawable.food_placeholder);
    }

    @Override
    public int getItemCount() {
        if( mRecipeList != null) {
            return mRecipeList.size();
        } else {
            return 0;
        }

    }

    public void updateAdapter(List<Recipe> recipeList) {
        mRecipeList = recipeList;
        notifyDataSetChanged();
    }
}
