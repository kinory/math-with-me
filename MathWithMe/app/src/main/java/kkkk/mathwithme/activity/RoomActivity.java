package kkkk.mathwithme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.Exercise;
import kkkk.mathwithme.model.ExerciseGenerator;
import kkkk.mathwithme.model.LinearEquation;
import kkkk.mathwithme.model.QuadraticEquation;
import kkkk.mathwithme.model.server.ServerAPI;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ServerAPI.Room room = (ServerAPI.Room) getIntent().getSerializableExtra("room");
        int type = room.getLevel() % 2 + 1;
        int level = room.getLevel() / 3 + 1;
        Exercise exercise = new ExerciseGenerator(room.getSeed(), type, level).generateExercise();
        int[] params = exercise.getParameters();
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
    }
}
