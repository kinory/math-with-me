package kkkk.mathwithme.model;

import android.content.Context;
import android.content.SharedPreferences;

import kkkk.mathwithme.R;

/**
 * Created by Gilad Kinory on 15/04/2016.
 * giladkinory2000@gmail.com
 */
public class LocalDatabaseAPI {

    private static final String ID_KEY = "id_key";
    private static final String USERNAME_KEY = "username_key";

    private Context context;

    public LocalDatabaseAPI(Context context) {
        this.context = context;
    }

    public String[] detailsFromHistory() {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        return new String[]{preferences.getString(ID_KEY, null), preferences.getString(USERNAME_KEY, null)};
    }

    public void detailsToHistory(String id, String username) {
        SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ID_KEY, id);
        editor.putString(USERNAME_KEY, username);
        editor.apply();
    }

}
