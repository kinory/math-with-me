package kkkk.mathwithme.model.server;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

    public void getAllRooms(final CallableWithParameter<List<Room>, Void> actionWhenDone,
                            final Callable<Void> actionIfFail) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                SERVER_URL + "/getallrooms", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Room> roomObjectList = new ArrayList<>();
                Iterator<String> iterator = response.keys();
                for (String key = ""; iterator.hasNext();) {
                    key = iterator.next();
                    System.out.println(key);
                    try {
                        roomObjectList.add(new Room(response.getString(key)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                actionWhenDone.call(roomObjectList);
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
                return super.getParams();
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public static class Room {
        private int level;
        private String id;
        private int seed;
        private int usersConnected;

        private Room(int level, String id, int seed, int usersConnected) {
            this.level = level;
            this.id = id;
            this.seed = seed;
            this.usersConnected = usersConnected;
        }

        public Room(String jsonString) {
            jsonString = jsonString.substring(3,jsonString.length() - 2);
            String[] array = jsonString.split("\\,");
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i].split("\\:")[1];
            }
            id = array[0].substring(1, array[0].length() - 1);
            level = Integer.parseInt(array[1].substring(1, array[1].length() - 1));
            usersConnected = Integer.parseInt(array[2].substring(1, array[2].length() - 1));
            seed = Integer.parseInt(array[3].substring(1, array[3].length() - 1));
        }

        public int getLevel() {
            return level;
        }

        public String getId() {
            return id;
        }

        public int getSeed() {
            return seed;
        }

        public int getUsersConnected() {
            return usersConnected;
        }
    }
}
