package com.example.juraj.bakingapp.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.juraj.bakingapp.IngredientsFragment;
import com.example.juraj.bakingapp.StepsFragment;
import com.example.juraj.bakingapp.data.model.Recipe;

/**
 * Created by Juraj on 3/22/2018.
 */

public class RecipeDetailsPagerAdapter extends FragmentPagerAdapter {

    final int PAGES = 2;
    private String tabs[] = { "Ingredients", "Steps"};

    private Context mContext;
    private Recipe mRecipe;

    public RecipeDetailsPagerAdapter(FragmentManager fragmentManager, Context context, Recipe recipe) {
        super(fragmentManager);
        mContext = context;
        mRecipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return IngredientsFragment.newInstance(mRecipe);
            }
            case 1: {
                return StepsFragment.newInstance(mRecipe);
            }
            default:
                return StepsFragment.newInstance(mRecipe);
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
