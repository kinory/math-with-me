package kkkk.mathwithme.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.LocalDatabaseAPI;
import kkkk.mathwithme.model.server.CallableWithParameter;
import kkkk.mathwithme.model.server.ServerAPI;

public class LoginActivity extends Activity {

    private String id;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private LocalDatabaseAPI databaseAPI;
    private ProgressBar progressBar;
    private TextView loginTextButton;
    private TextView signUpTextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseAPI = new LocalDatabaseAPI(this);

        id = databaseAPI.detailsFromHistory()[0];

        if (id != null) {
            finish();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_log_in);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);

        loginTextButton = (TextView) findViewById(R.id.loginTextButton);
        loginTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerAPI serverAPI = new ServerAPI(LoginActivity.this);
                progressBar.setVisibility(View.VISIBLE);
                final String username = usernameEditText.getText().toString();
                serverAPI.signIn(username,
                        passwordEditText.getText().toString(),
                        new CallableWithParameter<String, Void>() {
                            @Override
                            public Void call(String parameter) {
                                progressBar.setVisibility(View.GONE);
                                databaseAPI.detailsToHistory(parameter, username);
                                finish();
                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                startActivity(intent);
                                return null;
                            }
                        }, new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                progressBar.setVisibility(View.GONE);
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setTitle("Log in error").setMessage(R.string.login_error_message);
                                builder.setNegativeButton("Close", null);
                                builder.show();
                                return null;
                            }
                        });
            }
        });

        signUpTextButton = (TextView) findViewById(R.id.signUpNowTextButton);
        ServerAPI serverAPI = new ServerAPI(this);
        serverAPI.getAllRooms(new CallableWithParameter<List<ServerAPI.Room>, Void>() {
            @Override
            public Void call(List<ServerAPI.Room> parameter) {
                return null;
            }
        }, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                return null;
            }
        });
        signUpTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });


    }
}
