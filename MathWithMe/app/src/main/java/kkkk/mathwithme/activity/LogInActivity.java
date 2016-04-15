package kkkk.mathwithme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import kkkk.mathwithme.R;

public class LogInActivity extends Activity {

    private int id;
    private TextView signUpTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = idFromHistory();

        if (id == 0) {
            finish();
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);
            return;
        }

        setContentView(R.layout.activity_log_in);
        signUpTextButton = (TextView) findViewById(R.id.signUpNowTextButton);
        signUpTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private int idFromHistory() {
        return 0;
    }
}
