package com.example.juraj.bakingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juraj.bakingapp.data.adapters.IngredientsAdapter;
import com.example.juraj.bakingapp.data.adapters.StepsAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;


/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private IngredientsAdapter mIngredientsAdapter;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("INGREDIENTS", recipe);
        IngredientsFragment fragment = new IngredientsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        Recipe recipe = (Recipe) getArguments().getSerializable("INGREDIENTS");

        mRecyclerView = rootView.findViewById(R.id.ingredient_recyclerview);
        mIngredientsAdapter = new IngredientsAdapter(recipe.getIngredients(), getContext());
        mRecyclerView.setAdapter(mIngredientsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if( recipe != null) {
            mIngredientsAdapter.updateAdapter(recipe.getIngredients());
            Log.e("StepsFragment", "Recipe is great");
        } else {
            Log.e("StepsFragment", "Recipe is null");
        }

        return rootView;
    }

}
