package kkkk.mathwithme.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import io.github.kexanie.library.MathView;
import kkkk.mathwithme.R;
import kkkk.mathwithme.model.Exercise;
import kkkk.mathwithme.model.ExerciseGenerator;
import kkkk.mathwithme.model.LinearEquation;
import kkkk.mathwithme.model.QuadraticEquation;
import kkkk.mathwithme.model.server.ServerAPI;

public class RoomActivity extends AppCompatActivity {

    private LinearLayout messagesLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        messagesLinearLayout = (LinearLayout) findViewById(R.id.messagesLinearLayout);
        addMessages(null);


        MathView mathView = (MathView) findViewById(R.id.mathView);
        int seed = getIntent().getIntExtra("seed", 0);
        int levelInt = getIntent().getIntExtra("level", 1);
        int type = (int) Math.ceil(levelInt / 3.0);
        int level = (levelInt % 3) == 0 ? 3 : (levelInt % 3);
        Exercise exercise = new ExerciseGenerator(seed, type, level).generateExercise();
        int[] params = exercise.getParameters();
        String exerciseText = null;
        if (exercise.getType() == LinearEquation.class) {
            if (params[3] == 0 && params[5] == 0) {
                if (params[0] == 1 && params[1] == 1) {
                    exerciseText = String.format("(x+%d) = %d", params[2], params[8]);
                } else if (params[0] == 1) {
                    exerciseText = String.format("(%dx+%d) = %d", params[1], params[2], params[8]);
                } else if (params[1] == 1) {
                    exerciseText = String.format("%d(x+%d) = %d", params[0], params[2], params[8]);
                } else {
                    exerciseText = String.format("%d(%dx+%d) = %d", params[0], params[1], params[2], params[8]);
                }
            } else if(params[3] == 0) {
                exerciseText = String.format("%d(%dx+%d) = %d(%dx+%d)+%d", params[0], params[1], params[2], params[5], params[6], params[7], params[8]);
            } else if (params[5] == 0) {
                exerciseText = String.format("%d(%dx+%d)+%d(%dx+%d) = %d", params[0], params[1], params[2], params[3], params[4], params[5], params[8]);
            } else {
                exerciseText = String.format("%d(%dx+%d)+%d(x+%d) = %d(%dx+%d)+%d", params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);
            }
        } else if (exercise.getType() == QuadraticEquation.class) {
            if (params[2] == 0) {
                if (params[0] == 1 && params[1] == 1) {
                    exerciseText = "x^2+x = 0";
                } else if (params[0] == 1) {
                    exerciseText = String.format("x^2+%dx = 0", params[1]);
                } else if (params[1] == 1) {
                    exerciseText = String.format("%dx^2+x = 0", params[0]);
                } else {
                    exerciseText = String.format("%dx^2+%dx = 0", params[0], params[1]);
                }
                exerciseText = String.format("%dx^2+%dx = 0", params[0], params[1]);
            } else {
                if (params[0] == 1 && params[1] == 1) {
                    exerciseText = String.format("x^2+x = 0", params[2]);
                } else if (params[0] == 1) {
                    exerciseText = String.format("x^2+%dx+%d = 0", params[1], params[2]);
                } else if (params[1] == 1) {
                    exerciseText = String.format("%dx^2+x+%d = 0", params[0], params[2]);
                } else {
                    exerciseText = String.format("%dx^2+%dx+%d = 0", params[0], params[1], params[2]);
                }
            }
        }
        mathView.setText(String.format("$$%s$$", exerciseText));
    }

    public void addMessages(List<ServerAPI.Message> messages) {
//        for (ServerAPI.Message message : messages) {
        for (int i = 0; i < 10; i++) {
            View messageView = View.inflate(this, R.layout.chat_message, null);
            messageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 500));
            messagesLinearLayout.addView(messageView, i);
        }
//        }
    }
}
