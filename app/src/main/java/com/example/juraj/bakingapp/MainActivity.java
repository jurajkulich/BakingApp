package com.example.juraj.bakingapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.juraj.bakingapp.adapters.RecipesAdapter;
import com.example.juraj.bakingapp.data.model.Recipe;
import com.example.juraj.bakingapp.data.remote.ApiUtils;
import com.example.juraj.bakingapp.data.remote.RecipeService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecipesAdapter mRecipesAdapter;
    private List<Recipe> mRecipeList;

    private RecipeService mRecipeService;


    private @Nullable RecipesIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recipes_recyclerview);
        RecyclerView.ItemDecoration itemDecoration =  new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);
        mRecipesAdapter = new RecipesAdapter(mRecipeList, this);
        mRecyclerView.setAdapter(mRecipesAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecipeService = ApiUtils.getRecipeService();

        getIdlingResource();
        if( mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        loadResponse();
    }

    public void loadResponse() {
        mRecipeService.getResponse().enqueue(new Callback<List<Recipe>>() {

            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if( response.isSuccessful() ) {
                    mRecipesAdapter.updateAdapter(response.body());
                } else {
                    Log.e("MainActivity", "Response unsuccesful: " + response.code());
                }
                if( mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("MainActivity", "Response failure: " + t.toString());
                if( mIdlingResource != null) {
                    mIdlingResource.setIdleState(true);
                }
            }
        });
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if( mIdlingResource == null) {
            mIdlingResource = new RecipesIdlingResource();
        }
        return mIdlingResource;
    }
}
