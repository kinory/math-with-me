package kkkk.mathwithme.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import io.github.kexanie.library.MathView;
import kkkk.mathwithme.R;
import kkkk.mathwithme.model.ChatRoomAdapter;
import kkkk.mathwithme.model.Exercise;
import kkkk.mathwithme.model.ExerciseGenerator;
import kkkk.mathwithme.model.LinearEquation;
import kkkk.mathwithme.model.QuadraticEquation;
import kkkk.mathwithme.model.server.ServerAPI;

public class RoomActivity extends AppCompatActivity {

    TextView exerciseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ChatRoomAdapter(this));

        MathView mathView = (MathView) findViewById(R.id.mathView);
        exerciseTextView = new TextView(this);
        ServerAPI.Room room = (ServerAPI.Room) getIntent().getSerializableExtra("room");
        int type = room.getLevel() % 2 + 1;
        int level = room.getLevel() / 3 + 1;
        Exercise exercise = new ExerciseGenerator(room.getSeed(), type, level).generateExercise();
        int[] params = exercise.getParameters();
        String exerciseText = null;
        if (exercise.getType() == LinearEquation.class) {
            if (params[3] == 0 && params[5] == 0) {
                exerciseText = String.format("%d(%dx+%d) = %d", params[0], params[1], params[2], params[8]);
            } else if(params[3] == 0) {
                exerciseText = String.format("%d(%dx+%d) = %d(%dx+%d)+%d", params[0], params[1], params[2], params[5], params[6], params[7], params[8]);
            } else if (params[5] == 0) {
                exerciseText = String.format("%d(%dx+%d)+%d(%dx+%d) = %d", params[0], params[1], params[2], params[3], params[4], params[5], params[8]);
            } else {
                exerciseText = String.format("%d(%dx+%d) = %d", params[0], params[1], params[2], params[8]);
            }
        } else if (exercise.getType() == QuadraticEquation.class) {
            if (params[2] == 0) {
                exerciseText = String.format("%dx^2+%dx = 0", params[0], params[1]);
            } else {
                exerciseText = String.format("%dx^2+%dx+%d = 0", params[0], params[1], params[2]);
            }
        }
        mathView.setText(exerciseText);
    }
}
