package com.example.loisgussenhoven.walkabout.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;

import com.example.loisgussenhoven.walkabout.R;

import java.util.Locale;

/**
 * Created by Jaap-Jan on 18-12-2017.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private SharedPreferences preferences;
    private boolean showOptionsMenu;

    public BaseActivity() {
        showOptionsMenu = true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        preferences = PreferenceManager.getDefaultSharedPreferences(newBase);
        Locale language = new Locale(preferences.getString("language", "en"));
        Configuration configuration = newBase.getResources().getConfiguration();
        configuration.setLocale(language);
        Context newContext = newBase.createConfigurationContext(configuration);
        super.attachBaseContext(newContext);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showOptionsMenu)
            getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bar_help:
                Intent i = new Intent(this, InfoAppActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }

    protected void setLanguage(String language) {
        if (language.equals(preferences.getString("language", "en")))
            return; //Nothing to do.

        preferences.edit()
                .putString("language", language)
                .apply();

        recreate();
    }

    /**
     * Hides the toolbar's options-menu, which is otherwise inflated by default.
     * Note that calling this method has no use after {@link #onCreateOptionsMenu(Menu)}
     * has been called.
     *
     * @param value true if the menu should not be inflated, false otherwise.
     */
    protected void hideOptionsMenu(boolean value) {
        showOptionsMenu = !value;
    }
}
