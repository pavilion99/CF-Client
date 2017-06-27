package tech.scolton.cf_client.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import tech.scolton.cf_client.R;

public class AndroidStorage extends Storage {

    private Context context;

    public AndroidStorage(Context context) {
        this.context = context;
    }

    public boolean saveString(String key, String value) {
        SharedPreferences.Editor editor = this.getPreferenceEditor();

        editor.putString(key, value);

        editor.apply();

        return true;
    }

    public boolean saveInt(String key, int value) {
        SharedPreferences.Editor editor = this.getPreferenceEditor();

        editor.putInt(key, value);

        editor.apply();

        return true;
    }

    public boolean saveFloat(String key, float value) {
        SharedPreferences.Editor editor = this.getPreferenceEditor();

        editor.putFloat(key, value);

        editor.apply();

        return true;
    }

    public boolean saveBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.getPreferenceEditor();

        editor.putBoolean(key, value);

        editor.apply();

        return true;
    }

    public boolean saveStringSet (String key, Set<String> value) {
        SharedPreferences.Editor editor = this.getPreferenceEditor();

        editor.putStringSet(key, value);

        editor.apply();

        return true;
    }

    public String getString(String key) {
        SharedPreferences preferences = this.getPreferences();

        return preferences.getString(key, null);
    }

    public boolean getBoolean(String key) {
        SharedPreferences preferences = this.getPreferences();

        return preferences.getBoolean(key, false);
    }

    public int getInt(String key) {
        SharedPreferences preferences = this.getPreferences();

        return preferences.getInt(key, -1);
    }

    public float getFloat(String key) {
        SharedPreferences preferences = this.getPreferences();

        return preferences.getFloat(key, -1.0F);
    }

    public Set<String> getStringSet(String key) {
        SharedPreferences preferences = this.getPreferences();

        return preferences.getStringSet(key, null);
    }

    private SharedPreferences getPreferences() {
        return this.context.getSharedPreferences(this.context.getString(R.string.data_path), Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getPreferenceEditor() {
        return this.getPreferences().edit();
    }

}
