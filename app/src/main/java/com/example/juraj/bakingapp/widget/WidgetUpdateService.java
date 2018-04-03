package com.example.juraj.bakingapp.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.juraj.bakingapp.data.model.Recipe;

/**
 * Created by Juraj on 4/3/2018.
 */

public class WidgetUpdateService extends IntentService {

    public WidgetUpdateService() {
        super("WidgetUpdateService");
    }

    public static void updatingWidget(Context context, Recipe recipe) {
        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.putExtra("INGREDIENT", recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if( intent != null) {
            Recipe recipe = (Recipe) intent.getExtras().getSerializable("INGREDIENT");
            Log.d("WidgetUpdate", recipe.getName());
            Intent serviceIntent = new Intent("android.appwidget.action.APPWIDGET_UPDATE_INGREDIENTS");
            serviceIntent.setAction("android.appwidget.action.APPWIDGET_UPDATE_INGREDIENTS");
            serviceIntent.putExtra("INGREDIENT",recipe);
            sendBroadcast(serviceIntent);
        }
    }
}
