package kkkk.mathwithme.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.LocalDatabaseAPI;
import kkkk.mathwithme.model.server.CallableWithParameter;
import kkkk.mathwithme.model.server.ServerAPI;

public class MenuActivity extends AppCompatActivity {

    private static final int NUMBER_OF_CATEGORIES = 2;

    private ArrayList<FrameLayout> categories = new ArrayList<>();
    private TextView logOutTextButton;
    LocalDatabaseAPI databaseAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setContentView(R.layout.activity_menu);

        databaseAPI = new LocalDatabaseAPI(this);

        TextView welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        welcomeTextView.setText(databaseAPI.getUsername());

        LinearLayout menuLinearLayout = (LinearLayout) findViewById(R.id.menuLinearLayout);

        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++)
            categories.add((FrameLayout) menuLinearLayout.getChildAt(i));

        for (int i = 0; i < categories.size(); i++) {
            FrameLayout category = categories.get(i);
            final int finalI = i;
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                    final int[] difficulties = new int[]{1, 2, 3};
                    builder.setTitle(R.string.choose_difficulty_text).setItems(new String[]{"Easy", "Medium", "Hard"},
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    goToRoom(new ServerAPI.Room(difficulties[which] + (finalI - 1)*3, "",
                                            finalI, true));
                                }
                            }).setNegativeButton("Cancel", null);
                    builder.show();
                }
            });
        }



        logOutTextButton = (TextView) findViewById(R.id.logOutTextButton);
        logOutTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseAPI.delete();
                finish();
                Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void goToRoom(ServerAPI.Room room) {
        Intent intent = new Intent(this, RoomActivity.class);
        intent.putExtra("level", room.getLevel());
        intent.putExtra("seed", room.getSeed());
        startActivity(intent);
    }
}
