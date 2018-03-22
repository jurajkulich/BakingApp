package com.example.juraj.bakingapp.data.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juraj.bakingapp.R;
import com.example.juraj.bakingapp.data.model.Ingredient;
import com.example.juraj.bakingapp.data.model.Step;

import java.util.List;

/**
 * Created by Juraj on 3/22/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {

    private List<Ingredient> mIngredientList;
    private Context mContext;

    public IngredientsAdapter(List<Ingredient> ingredientList, Context context) {
        mIngredientList = ingredientList;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mIngredientNameTextView;

        public ViewHolder(View view) {
            super(view);
            mIngredientNameTextView = view.findViewById(R.id.item_ingredient_name);
        }
    }

    @Override
    public IngredientsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_ingredient, parent, false );

        IngredientsAdapter.ViewHolder viewHolder = new IngredientsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient ingredient = mIngredientList.get(position);

        TextView textView = holder.mIngredientNameTextView;
        textView.setText(ingredient.getQuantity() + " " + ingredient.getMeasure() + " of " + ingredient.getIngredient());
    }

    @Override
    public int getItemCount() {
        if( mIngredientList != null) {
            return mIngredientList.size();
        } else {
            return 0;
        }

    }

    public void updateAdapter(List<Ingredient> ingredientList) {
        mIngredientList = ingredientList;
        notifyDataSetChanged();
    }
}
