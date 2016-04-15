package kkkk.mathwithme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.Exercise;
import kkkk.mathwithme.model.ExerciseGenerator;
import kkkk.mathwithme.model.LinearEquation;
import kkkk.mathwithme.model.QuadraticEquation;
import kkkk.mathwithme.model.server.ServerAPI;

public class RoomActivity extends AppCompatActivity {

    TextView exerciseTextView;

    protected void drawXSquared(int )

    protected void drawEquation(int equationType, int[] params) {
        switch (equationType) {
            case 1:
                break;
            case 2:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        exerciseTextView = new TextView(this);
        ServerAPI.Room room = (ServerAPI.Room) getIntent().getSerializableExtra("room");
        int type = room.getLevel() % 2 + 1;
        int level = room.getLevel() / 3 + 1;
        Exercise exercise = new ExerciseGenerator(room.getSeed(), type, level).generateExercise();
        int[] params = exercise.getParameters();
        String exerciseText = null;
        if (exercise.getType() == LinearEquation.class) {
            if (params[0] == 0 && params[3] == 0 && params[1]==0) {

            }
        } else if (exercise.getType() == QuadraticEquation.class) {
            if (params[2] == 0) {
                String format = String.format("%d*x^2+%d*x", params[0], params[1]);
            } else {
                String format = String.format("%d*x^2+%d*x+%d", params[0], params[1], params[2]);
            }
        }
        exerciseTextView.setText(Html.fromHtml(exerciseText));
    }
}
