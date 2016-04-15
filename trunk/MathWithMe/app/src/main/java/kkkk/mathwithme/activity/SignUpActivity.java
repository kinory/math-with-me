package kkkk.mathwithme.activity;

import android.app.Activity;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;

import kkkk.mathwithme.R;

public class SignUpActivity extends Activity {

    private static final String SERVER_URL = "http://math-with-me.rapidapi.io/sendmessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
}
