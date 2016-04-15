package kkkk.mathwithme.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.Callable;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.server.CallableWithParameter;
import kkkk.mathwithme.model.server.ServerAPI;

public class LogInActivity extends Activity {

    private int id;
    private TextView signUpTextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = idFromHistory();

//        if (id != 0) {
//            finish();
//            Intent intent = new Intent(this, MenuActivity.class);
//            startActivity(intent);
//            return;
//        }

        setContentView(R.layout.activity_log_in);
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
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }

    private int idFromHistory() {
        return 0;
    }
}
