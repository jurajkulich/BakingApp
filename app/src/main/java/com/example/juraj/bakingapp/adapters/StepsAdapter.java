package com.example.juraj.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juraj.bakingapp.R;
import com.example.juraj.bakingapp.StepVideoActivity;
import com.example.juraj.bakingapp.StepVideoFragment;
import com.example.juraj.bakingapp.StepsFragment;
import com.example.juraj.bakingapp.data.model.Step;

import java.util.List;

/**
 * Created by Juraj on 3/20/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Step> mStepList;
    private Context mContext;
    private StepsFragment.OnStepClickListener mCallback;

    public StepsAdapter(List<Step> stepList, Context context, StepsFragment.OnStepClickListener clickListener) {
        mStepList = stepList;
        mContext = context;
        mCallback = clickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mStepNameTextView;

        public ViewHolder(View view) {
            super(view);
            mStepNameTextView = view.findViewById(R.id.item_step_name);
        }
    }

    @Override
    public StepsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        View view = layoutInflater.inflate(R.layout.item_step, parent, false );

        StepsAdapter.ViewHolder viewHolder = new StepsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Step step = mStepList.get(position);

        TextView textView = holder.mStepNameTextView;
        textView.setText(step.getShortDescription());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // StepVideoFragment stepVideoFragment = StepVideoFragment.newInstance(step);
                mCallback.onStepClickListener(position);
                /*
                Intent intent = new Intent(mContext, StepVideoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("STEP", step);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                 */


                /*
                android.support.v4.app.FragmentTransaction transaction =  ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.recipe_detail, stepVideoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
                */
            }
        });
    }

    @Override
    public int getItemCount() {
        if( mStepList != null) {
            return mStepList.size();
        } else {
            return 0;
        }

    }

    public void updateAdapter(List<Step> recipeList) {
        mStepList = recipeList;
        notifyDataSetChanged();
    }
}
