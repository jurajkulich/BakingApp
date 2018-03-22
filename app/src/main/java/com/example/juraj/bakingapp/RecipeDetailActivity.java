package com.example.juraj.bakingapp;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.juraj.bakingapp.data.adapters.RecipeDetailsPagerAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Recipe recipe =  (Recipe) getIntent().getExtras().getSerializable("RECIPE");
        /*
        if( savedInstanceState != null) {
            if( savedInstanceState.getSerializable("RECIPE") != null) {
                recipe = (Recipe) savedInstanceState.getSerializable("RECIPE");
                Log.e("RecipeDetail", recipe.getName());
            } else {
                Log.e("RecipeDetail", "Recipe is null");
            }
        }
        */
        ViewPager viewPager = findViewById(R.id.recipe_detail_wiewpager);
        viewPager.setAdapter(new RecipeDetailsPagerAdapter(getSupportFragmentManager(), this, recipe));

        TabLayout tabLayout = findViewById(R.id.recipe_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}
