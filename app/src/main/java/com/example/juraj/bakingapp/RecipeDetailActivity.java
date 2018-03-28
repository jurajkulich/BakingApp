package com.example.juraj.bakingapp;

import android.app.FragmentTransaction;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.juraj.bakingapp.adapters.RecipeDetailsPagerAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        Recipe recipe =  (Recipe) getIntent().getExtras().getSerializable("RECIPE");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        ViewPager viewPager = findViewById(R.id.recipe_detail_wiewpager);
        viewPager.setAdapter(new RecipeDetailsPagerAdapter(getSupportFragmentManager(), this, recipe));

        TabLayout tabLayout = findViewById(R.id.recipe_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);
        */

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.ingredients_fragment_container, IngredientsFragment.newInstance(recipe)).commit();
        fragmentManager.beginTransaction().add(R.id.steps_fragment_container, StepsFragment.newInstance(recipe)).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
