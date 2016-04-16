package kkkk.mathwithme.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import io.github.kexanie.library.MathView;
import kkkk.mathwithme.R;
import kkkk.mathwithme.model.Exercise;
import kkkk.mathwithme.model.ExerciseGenerator;
import kkkk.mathwithme.model.LinearEquation;
import kkkk.mathwithme.model.LocalDatabaseAPI;
import kkkk.mathwithme.model.QuadraticEquation;
import kkkk.mathwithme.model.server.CallableWithParameter;
import kkkk.mathwithme.model.server.ServerAPI;

public class RoomActivity extends AppCompatActivity {

    private LinearLayout messagesLinearLayout;
    private Button checkButton;

    private EditText answerEditText1;
    private EditText answerEditText2;

    private ServerAPI serverAPI;
    private LocalDatabaseAPI databaseAPI;

    private long lastRequestTime = 0;

    private ImageButton sendMessageButton;
    private EditText messageEditText;

    private ServerAPI.Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        messagesLinearLayout = (LinearLayout) findViewById(R.id.messagesLinearLayout);

        serverAPI = new ServerAPI(this);
        databaseAPI = new LocalDatabaseAPI(this);

        answerEditText1 = (EditText) findViewById(R.id.answerTextField1);
        answerEditText2 = (EditText) findViewById(R.id.answerTextField2);

        answerEditText2.setVisibility(View.GONE);

        MathView mathView = (MathView) findViewById(R.id.mathView);
        room = (ServerAPI.Room) getIntent().getSerializableExtra("room");
        int seed = room.getSeed();
        int levelInt = room.getLevel();
        int type = (int) Math.ceil(levelInt / 3.0);
        int level = (levelInt % 3) == 0 ? 3 : (levelInt % 3);
        final Exercise exercise = new ExerciseGenerator(seed, type, level).generateExercise();
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
            answerEditText2.setVisibility(View.VISIBLE);
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

        checkButton = (Button) findViewById(R.id.checkButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] solutions = exercise.getSolutions();
                int solution1 = Integer.parseInt(answerEditText1.getText().toString());
                if (exercise.getType() == QuadraticEquation.class) {
                    int solution2 = Integer.parseInt(answerEditText2.getText().toString());
                    if ((solution1 == solutions[0] && solution2 == solutions[1]) ||
                            (solution2 == solutions[0] && solution1 == solutions[1])) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                        builder.setTitle("Well Done!").setMessage("Join your other group members and help them complete the exercise");
                        builder.setNegativeButton("Close", null);
                        builder.show();
                        checkButton.setClickable(false);
                        answerEditText1.setClickable(false);
                        answerEditText2.setClickable(false);
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                        builder.setTitle("Wrong Answer").setMessage("Try again...");
                        builder.setNegativeButton("Close", null);
                        builder.show();
                    }
                } else if (solution1 == solutions[0]) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                    builder.setTitle("Well Done!").setMessage("Join your other group members and help them complete the exercise");
                    builder.setNegativeButton("Close", null);
                    builder.show();
                    checkButton.setClickable(false);
                    answerEditText1.setClickable(false);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
                    builder.setTitle("Wrong Answer").setMessage("Try again...");
                    builder.setNegativeButton("Close", null);
                    builder.show();
                }
            }
        });

        messageEditText = (EditText) findViewById(R.id.messageEditText);
        sendMessageButton = (ImageButton) findViewById(R.id.sendMessageButton);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                serverAPI.sendMessage(databaseAPI.getUsername(), messageEditText.getText().toString(), room.getId(), new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                }, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                });
            }
        });

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                serverAPI.receiveMessages(lastRequestTime, room.getId(), new CallableWithParameter<List<ServerAPI.Message>, Void>() {
                    @Override
                    public Void call(List<ServerAPI.Message> parameter) {
                        addMessages(parameter);
                        return null;
                    }
                }, new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {
                        return null;
                    }
                });
                lastRequestTime = System.currentTimeMillis();
            }
        }, 10, 1000);
    }

    public void addMessages(List<ServerAPI.Message> messages) {
        for (final ServerAPI.Message message : messages) {
            final View messageView = View.inflate(this, R.layout.chat_message, null);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    TextView messageTextView = (TextView) messageView.findViewById(R.id.messageTextView);
                    TextView usernameTextView = (TextView) messageView.findViewById(R.id.usernameTextView);
                    TextView timeTextView = (TextView) messageView.findViewById(R.id.timeTextView);


                    messageTextView.setText(message.getMessage());
                    usernameTextView.setText(message.getSenderUserName());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    timeTextView.setText(simpleDateFormat.format(new Date(message.getTimeSent())));

                    messageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,500));
                    messagesLinearLayout.addView(messageView);
                }
            });
            }
        }
}
