package kkkk.mathwithme.model.server;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Gilad Kinory on 15/04/2016.
 * giladkinory2000@gmail.com
 */
public class ServerAPI {

    public static final String TAG = "ServerAPI";
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

    public void getUserById(final String id,
                            final CallableWithParameter<UserWithoutRoom, Void> actionWhenDone,
                            final Callable<Void> actionIfFail) {
        new AsyncTask<Void, Void,Void>() {
            @Override
            protected Void doInBackground(Void[] params) {
                OkHttpClient okHttpClient = new OkHttpClient();
                try {
                    RequestBody formBody = new FormBody.Builder()
                            .add("userId", id)
                            .build();

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(SERVER_URL+"/getuserbyid")
                            .post(formBody)
                            .build();

                    okhttp3.Response response = okHttpClient.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected Code: " + response);
                    } else {
                        actionWhenDone.call(new UserWithoutRoom(new JSONObject(response.body().string())));
                    }
                } catch (IOException|JSONException e) {
                    try {
                        actionIfFail.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();

    }

    public void getAllRooms(final CallableWithParameter<List<Room>, Void> actionWhenDone,
                            final Callable<Void> actionIfFail) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                SERVER_URL + "/getallrooms", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ArrayList<Room> roomObjectList = new ArrayList<>();
                Iterator<String> iterator = response.keys();
                try {
                    String rooms = response.getString(iterator.next());
                    JSONArray jsonArray = new JSONArray(rooms);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        roomObjectList.add(new Room(jsonArray.getString(i)));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                actionWhenDone.call(roomObjectList);
            }
        }
                , new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    actionIfFail.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        )

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

    public void sendMessage(final String senderId, final String message,
                            final Callable<Void> actionWhenDone,
                            final Callable<Void> actionIfFail) {
        new AsyncTask<Void, Void, Void>() {
            OkHttpClient client = new OkHttpClient();
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    RequestBody formBody = new FormBody.Builder()
                            .add("message", message)
                            .add("userId", senderId)
                            .add("timeSent", String.valueOf(System.currentTimeMillis()))
                            .build();
                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url(SERVER_URL+"/sendmessage")
                            .post(formBody)
                            .build();
                    okhttp3.Response response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        throw new IOException("Unexpected Code: " + response);
                    } else {
                        actionWhenDone.call();
                    }
                } catch (Exception e) {
                    try {
                        actionIfFail.call();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                return null;
            }
        }.execute();
    }


    public static class Room {
        private int level;
        private String id;
        private int seed;
        private boolean roomStarted;

        private Room(int level, String id, int seed, boolean roomStarted) {
            this.level = level;
            this.id = id;
            this.seed = seed;
            this.roomStarted = roomStarted;
        }

        public Room(String jsonString) {
            System.out.println(jsonString);
            jsonString = jsonString.substring(1, jsonString.length() - 1);
            String[] array = jsonString.split("\\,");
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i].split("\\:")[1];
            }
            id = array[0].substring(1, array[0].length() - 1);
            level = Integer.parseInt(array[1].substring(1, array[1].length() - 1));
            roomStarted = Integer.parseInt(array[3].substring(1, array[3].length() - 1)) != 0;
            seed = Integer.parseInt(array[2].substring(1, array[2].length() - 1));
            System.out.println("Id" + id + " level" + level + " roomstarted" + roomStarted + " seed" + seed);
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

        public boolean getRoomStarted() {
            return roomStarted;
        }
    }

    public static class UserWithoutRoom {
        private String id;
        private String userName;
        private String email;
        private int points;
        private String username;

        private UserWithoutRoom(String id, String userName, String email, int points) {
            this.id = id;
            this.userName = userName;
            this.email = email;
            this.points = points;
        }

        public UserWithoutRoom(JSONObject response) throws JSONException {
            id = response.getString("_id");
            userName = response.getString("username");
            email = response.getString("email");
            points = Integer.parseInt(response.getString("points"));
        }

        public int getPoints() {
            return points;
        }

        public String getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }
    }
}
