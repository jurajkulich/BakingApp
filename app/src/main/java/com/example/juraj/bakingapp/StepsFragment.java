package com.example.juraj.bakingapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.juraj.bakingapp.adapters.StepsAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;
import com.example.juraj.bakingapp.data.model.Step;

import java.util.List;


public class StepsFragment extends Fragment {

    private List<Step> mStepList;
    private RecyclerView mRecyclerView;
    private StepsAdapter mStepsAdapter;


    public StepsFragment() {
        // Required empty public constructor
    }

    public static StepsFragment newInstance(Recipe recipe) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("STEPS", recipe);
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        Recipe recipe = (Recipe) getArguments().getSerializable("STEPS");

        mRecyclerView = rootView.findViewById(R.id.steps_recyclerview);
        mStepsAdapter = new StepsAdapter(mStepList, getContext());
        mRecyclerView.setAdapter(mStepsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if( recipe != null) {
            mStepsAdapter.updateAdapter(recipe.getSteps());
            Log.e("StepsFragment", "Recipe is great");
        } else {
            Log.e("StepsFragment", "Recipe is null");
        }

        return rootView;
    }
}
