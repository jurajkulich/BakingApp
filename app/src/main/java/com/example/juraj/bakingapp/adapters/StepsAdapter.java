package com.example.juraj.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
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

    private ViewHolder mViewHolder;

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

    public int getViewHolder() {
        if( mViewHolder == null) {
            return -1;
        }
        return mViewHolder.getAdapterPosition();
    }

    public void setViewHolder(ViewHolder viewHolder) {
        if( viewHolder != null) {
            mViewHolder = viewHolder;
            mViewHolder.itemView.setSelected(true);
            Log.e("Viewholder", mViewHolder.getAdapterPosition()+"");
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Step step = mStepList.get(position);

        TextView textView = holder.mStepNameTextView;
        textView.setText(step.getShortDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onStepClickListener(position);

                if( mViewHolder != null) {
                    mViewHolder.itemView.setSelected(false);
                }
                mViewHolder = holder;
                mViewHolder.itemView.setSelected(true);
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
