package com.example.juraj.bakingapp.data.remote;

import com.example.juraj.bakingapp.data.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by juraj on 3/19/18.
 */

public interface RecipeService {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getResponse();
}
