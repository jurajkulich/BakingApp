package com.example.juraj.bakingapp;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.juraj.bakingapp.adapters.RecipeDetailsPagerAdapter;
import com.example.juraj.bakingapp.adapters.RecipesAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;
import com.example.juraj.bakingapp.widget.WidgetUpdateService;

public class RecipeDetailActivity extends AppCompatActivity implements StepsFragment.OnStepClickListener{

    private static final String positionBundle = "selectedPos";
    public static final String stepBundle = "STEP";

    private Recipe recipe;

    private boolean mTwoPane;

    private int selectedPos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if( getIntent().getExtras() != null) {
            recipe  =  (Recipe) getIntent().getExtras().getSerializable(RecipesAdapter.recipeBundle);
        }

        if( savedInstanceState != null) {
            selectedPos = savedInstanceState.getInt(positionBundle);
        }

        if( findViewById(R.id.recipe_detail_two_pane) != null && selectedPos == -1) {
            mTwoPane = true;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_video_fragment_container, StepVideoFragment.newInstance(recipe.getSteps().get(0))).commit();
        } else if(findViewById(R.id.recipe_detail_two_pane) == null){
            mTwoPane = false;
        } else {
            mTwoPane = true;
        }

        ViewPager viewPager = findViewById(R.id.recipe_detail_wiewpager);
        viewPager.setAdapter(new RecipeDetailsPagerAdapter(this, getSupportFragmentManager(), recipe, mTwoPane));

        TabLayout tabLayout = findViewById(R.id.recipe_detail_tabs);
        tabLayout.setupWithViewPager(viewPager);

        WidgetUpdateService.updatingWidget(this, recipe);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(positionBundle, selectedPos);
    }

    @Override
    public void onStepClickListener(int position) {
        selectedPos = position;
        if( mTwoPane) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.step_video_fragment_container, StepVideoFragment.newInstance(recipe.getSteps().get(position))).commit();
        } else {
            Intent intent = new Intent(this, StepVideoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(stepBundle, recipe.getSteps().get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
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
