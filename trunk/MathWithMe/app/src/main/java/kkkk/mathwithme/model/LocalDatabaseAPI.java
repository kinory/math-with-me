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
    private static final String EMAIL_KEY = "email_key";

    private Context context;

    private SharedPreferences sharedPreferences;

    public LocalDatabaseAPI(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
    }

    public String getId() {
        return sharedPreferences.getString(ID_KEY, null);
    }

    public String getUsername() {
        return sharedPreferences.getString(USERNAME_KEY, null);
    }

    public String getEmail() {
        return sharedPreferences.getString(EMAIL_KEY, null);
    }

    public void setId(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_KEY, id);
        editor.apply();
    }

    public void setUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_KEY, username);
        editor.apply();
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(EMAIL_KEY, email);
        editor.apply();
    }

    public void delete() {
        setId(null);
        setUsername(null);
        setEmail(null);
    }

}
