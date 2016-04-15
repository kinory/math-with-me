package kkkk.mathwithme.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import kkkk.mathwithme.R;
import kkkk.mathwithme.model.Exercise;
import kkkk.mathwithme.model.ExerciseGenerator;
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
        switch (exercise.getType()) {

        }
    }
}
