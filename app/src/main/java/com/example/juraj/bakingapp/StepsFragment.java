package com.example.juraj.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;


public class StepsFragment extends Fragment {

    private static final String stepsBundle = "STEPS";
    private static final String adapterPosBundle = "adapterPos";
    private static final String twoPaneBundle = "isTwoPane";

    private RecyclerView mRecyclerView;
    private StepsAdapter mStepsAdapter;

    OnStepClickListener callback;

    private int adapterPos;

    private boolean mIsTwoPane;

    public interface OnStepClickListener {
        void onStepClickListener(int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public StepsFragment() {
        // Required empty public constructor
    }

    public static StepsFragment newInstance(Recipe recipe, boolean isTwoPane) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(stepsBundle, recipe);
        bundle.putBoolean(twoPaneBundle, isTwoPane);
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( savedInstanceState != null) {
            adapterPos = savedInstanceState.getInt(adapterPosBundle);
            mIsTwoPane = savedInstanceState.getBoolean(twoPaneBundle);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_steps, container, false);

        Recipe recipe = (Recipe) getArguments().getSerializable(stepsBundle);
        mIsTwoPane = getArguments().getBoolean(twoPaneBundle);

        mRecyclerView = rootView.findViewById(R.id.steps_recyclerview);
        mStepsAdapter = new StepsAdapter(new ArrayList<Step>(), getContext(), callback, mIsTwoPane);
        Log.e("StepsFragment", mIsTwoPane+"");
        mRecyclerView.setAdapter(mStepsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if( recipe != null) {
            mStepsAdapter.updateAdapter(recipe.getSteps());
        } else {
            Log.e("StepsFragment", "Recipe is null");
        }

        restoreState();

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(adapterPosBundle, mStepsAdapter.getViewHolder());
        outState.putBoolean(twoPaneBundle, mIsTwoPane);
    }

    private void restoreState() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RecyclerView.ViewHolder viewHolder =  mRecyclerView.findViewHolderForAdapterPosition(adapterPos);
                if( viewHolder != null ) {
                    mStepsAdapter.setViewHolder((StepsAdapter.ViewHolder)viewHolder);
                }
            }
        }, 500);
    }

}
