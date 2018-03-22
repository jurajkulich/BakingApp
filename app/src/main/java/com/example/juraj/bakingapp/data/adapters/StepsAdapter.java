package com.example.juraj.bakingapp.data.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.juraj.bakingapp.R;
import com.example.juraj.bakingapp.data.model.Step;

import java.util.List;

/**
 * Created by Juraj on 3/20/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.ViewHolder> {

    private List<Step> mStepList;
    private Context mContext;

    public StepsAdapter(List<Step> stepList, Context context) {
        mStepList = stepList;
        mContext = context;
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Step step = mStepList.get(position);

        TextView textView = holder.mStepNameTextView;
        textView.setText(step.getShortDescription());
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
