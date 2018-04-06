package com.example.juraj.bakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juraj.bakingapp.adapters.IngredientsAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    private static final String ingredientsBundle = "INGREDIENTS";

    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(ingredientsBundle, recipe);
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        Recipe recipe = (Recipe) getArguments().getSerializable(ingredientsBundle);

        mRecyclerView = rootView.findViewById(R.id.ingredient_recyclerview);
        mIngredientsAdapter = new IngredientsAdapter(recipe.getIngredients(), getContext());
        mRecyclerView.setAdapter(mIngredientsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if( recipe != null) {
            mIngredientsAdapter.updateAdapter(recipe.getIngredients());
        } else {
            Log.e("StepsFragment", "Recipe is null");
        }

        return rootView;
    }

}
