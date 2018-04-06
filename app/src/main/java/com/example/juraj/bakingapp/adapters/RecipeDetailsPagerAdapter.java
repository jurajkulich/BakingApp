package com.example.juraj.bakingapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.example.juraj.bakingapp.IngredientsFragment;
import com.example.juraj.bakingapp.R;
import com.example.juraj.bakingapp.StepsFragment;
import com.example.juraj.bakingapp.data.model.Recipe;

/**
 * Created by Juraj on 3/22/2018.
 */

public class RecipeDetailsPagerAdapter extends FragmentPagerAdapter {

    final int PAGES = 2;

    private Context mContext;
    private Recipe mRecipe;
    private boolean mIsTwoPane;

    private String[] tabs;

    public RecipeDetailsPagerAdapter(Context context, FragmentManager fragmentManager, Recipe recipe, boolean isTwoPane) {
        super(fragmentManager);
        mContext = context;
        mRecipe = recipe;
        mIsTwoPane = isTwoPane;

        tabs = new String[]{ mContext.getResources().getString(R.string.tab_ingredients),
                mContext.getResources().getString(R.string.tab_steps)};

        Log.e("PagerAdapter", mIsTwoPane + "");

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return IngredientsFragment.newInstance(mRecipe);
            }
            case 1: {
                return StepsFragment.newInstance(mRecipe, mIsTwoPane);
            }
            default:
                return StepsFragment.newInstance(mRecipe, mIsTwoPane);
        }
    }

    @Override
    public int getCount() {
        return PAGES;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
