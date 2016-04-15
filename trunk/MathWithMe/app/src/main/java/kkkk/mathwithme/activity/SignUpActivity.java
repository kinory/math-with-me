package kkkk.mathwithme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.Callable;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.LocalDatabaseAPI;
import kkkk.mathwithme.model.server.CallableWithParameter;
import kkkk.mathwithme.model.server.ServerAPI;

public class SignUpActivity extends Activity {

    private static final String SERVER_URL = "http://math-with-me.rapidapi.io/sendmessage";

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText emailEditText;

    private TextView signUpTextButton;

    private LocalDatabaseAPI databaseAPI;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        repeatPasswordEditText = (EditText) findViewById(R.id.repeatPasswordEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);

        signUpTextButton = (TextView) findViewById(R.id.signUpTextButton);
        signUpTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!passwordEditText.getText().toString().equals(repeatPasswordEditText.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                    builder.setTitle("Sign Up Error").setMessage("Password doesn't match the password confirmation. Please try again.");
                    builder.show();
                } else {
                    final String username = usernameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String email = emailEditText.getText().toString();

                    progressBar.setVisibility(View.VISIBLE);

                    databaseAPI = new LocalDatabaseAPI(SignUpActivity.this);
                    final ServerAPI serverAPI = new ServerAPI(SignUpActivity.this);
                    serverAPI.signUp(username, password, email, new CallableWithParameter<String, Void>() {
                        @Override
                        public Void call(String parameter) {
                            progressBar.setVisibility(View.GONE);
                            databaseAPI.setId(parameter);
                            databaseAPI.setUsername(username);
                            finish();
                            Intent intent = new Intent(SignUpActivity.this, MenuActivity.class);
                            startActivity(intent);
                            return null;
                        }
                    }, new Callable<Void>() {
                        @Override
                        public Void call() throws Exception {
                            progressBar.setVisibility(View.GONE);
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
                            builder.setTitle("Sign up error").setMessage(R.string.sign_up_error_message);
                            builder.setNegativeButton("Close", null);
                            builder.show();
                            return null;
                        }
                    });
                }
            }
        });
    }
}
