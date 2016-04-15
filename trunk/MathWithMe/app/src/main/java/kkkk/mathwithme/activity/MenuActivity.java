package kkkk.mathwithme.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.LocalDatabaseAPI;

public class MenuActivity extends AppCompatActivity {

    private static final int NUMBER_OF_CATEGORIES = 2;

    private int id;
    private ArrayList<FrameLayout> categories = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        setContentView(R.layout.activity_menu);

        LocalDatabaseAPI databaseAPI = new LocalDatabaseAPI(this);

        TextView welcomeTextView = (TextView) findViewById(R.id.welcomeTextView);
        welcomeTextView.setText("Welcome " + databaseAPI.detailsFromHistory()[1] + "!");

        LinearLayout menuLinearLayout = (LinearLayout) findViewById(R.id.menuLinearLayout);

        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++)
            categories.add((FrameLayout) menuLinearLayout.getChildAt(i));

        for (FrameLayout category : categories) {
            category.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MenuActivity.this);
                    builder.setTitle(R.string.choose_difficulty_text).setItems(new String[]{"Easy", "Medium", "Hard"},
                            new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Do something
                        }
                    }).setNegativeButton("Cancel", null);
                    builder.show();
                }
            });
        }
    }
}
