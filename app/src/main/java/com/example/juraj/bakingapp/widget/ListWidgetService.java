package com.example.juraj.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.juraj.bakingapp.R;
import com.example.juraj.bakingapp.data.model.Ingredient;
import com.example.juraj.bakingapp.data.model.Recipe;

import java.util.List;

/**
 * Created by Juraj on 4/2/2018.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipeRemoteViewsFactory(getApplicationContext(), intent);
    }
}


class RecipeRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe mRecipe;

    RecipeRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipe = RecipeWidget.sRecipe;
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if( mRecipe.getIngredients() != null)
            return mRecipe.getIngredients().size();
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int i) {

        List<Ingredient> ingredients = mRecipe.getIngredients();

        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_ingredient_item);
        remoteViews.setTextViewText(R.id.widget_ingredient_item_name, String.valueOf(ingredients.get(i).getIngredient()));
        remoteViews.setTextViewText(R.id.widget_ingredient_item_measure, String.valueOf(ingredients.get(i).getQuantity() + " " + ingredients.get(i).getMeasure()));
        remoteViews.setTextViewText(R.id.widget_recipe_name, mRecipe.getName());

        Intent intent = new Intent();
        remoteViews.setOnClickFillInIntent(R.id.widget_ingredient_item, intent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
