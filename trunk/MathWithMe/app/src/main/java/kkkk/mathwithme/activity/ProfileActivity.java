package kkkk.mathwithme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.Callable;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.LocalDatabaseAPI;
import kkkk.mathwithme.model.server.CallableWithParameter;
import kkkk.mathwithme.model.server.ServerAPI;

public class ProfileActivity extends Activity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView pointsTextView;

    private LinearLayout mainLinearLayout;
    private ProgressBar progressBar;

    private LocalDatabaseAPI databaseAPI;
    private ServerAPI serverAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setContentView(R.layout.activity_profile);

        mainLinearLayout = (LinearLayout) findViewById(R.id.mainLinearLayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar3);

        mainLinearLayout.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        databaseAPI = new LocalDatabaseAPI(this);
        serverAPI = new ServerAPI(this);

        usernameTextView = (TextView) findViewById(R.id.userNameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);

        serverAPI.getUserById(databaseAPI.getId(), new CallableWithParameter<ServerAPI.UserWithoutRoom, Void>() {
            @Override
            public Void call(final ServerAPI.UserWithoutRoom parameter) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        usernameTextView.setText("Username: " + parameter.getUsername());
                        emailTextView.setText("Email: " + parameter.getEmail());
                        pointsTextView.setText("Points: " + parameter.getPoints());

                        mainLinearLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                });

                return null;
            }
        }, new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                finish();
                return null;
            }
        });
    }
}
