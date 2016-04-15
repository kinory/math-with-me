package kkkk.mathwithme.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.LocalDatabaseAPI;
import kkkk.mathwithme.model.server.ServerAPI;

public class ProfileActivity extends Activity {

    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView pointsTextView;

    private LocalDatabaseAPI databaseAPI;
    private ServerAPI serverAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setContentView(R.layout.activity_profile);

        databaseAPI = new LocalDatabaseAPI(this);
        serverAPI = new ServerAPI(this);

        usernameTextView = (TextView) findViewById(R.id.userNameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        pointsTextView = (TextView) findViewById(R.id.pointsTextView);

        usernameTextView.setText("Username: " + databaseAPI.getUsername());
        emailTextView.setText("Email: " + databaseAPI.getEmail());
        pointsTextView.setText("Points: 0");
    }
}
