package kkkk.mathwithme.model.server;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by Gilad Kinory on 15/04/2016.
 * giladkinory2000@gmail.com
 */
public class ServerAPI {

    public static final String SERVER_URL = "http://math-with-me.rapidapi.io";
    private final RequestQueue requestQueue;

    public ServerAPI(Context context) {
        requestQueue = Volley.newRequestQueue(context);
    }

    public void signUp(final String username, final String password, final String email,
                       final CallableWithParameter<String, Void> actionWhenDone,
                       final Callable<Void> actionIfFail) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL + "/signup",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        actionWhenDone.call(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    actionIfFail.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mParams = new HashMap<>();
                mParams.put("email", email);
                mParams.put("username", username);
                mParams.put("password", password);
                return mParams;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void signIn(final String username, final String password,
                       final CallableWithParameter<String, Void> actionWhenDone,
                       final Callable<Void> actionIfFail) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SERVER_URL + "/signin",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        actionWhenDone.call(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    actionIfFail.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mParams = new HashMap<>();
                mParams.put("username", username);
                mParams.put("password", password);
                return mParams;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void getAllRooms(CallableWithParameter<List<Room>, Void> actionWhenDone,
                            Callable<Void> actionIfFail) {

    }

    private static class Room {
        private int level;
        private int id;
    }
}
