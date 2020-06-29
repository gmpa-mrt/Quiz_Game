package com.example.game_learning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView title;
    private Integer user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.title = findViewById(R.id.main_title);
        final Button quiz = findViewById(R.id.main_btn_quiz);
        final Button create = findViewById(R.id.main_btn_create);
        final Button stats = findViewById(R.id.main_btn_stats);
        final ImageButton logout = findViewById(R.id.main_btn_logout);

        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getInt("userId");
        Log.i ("ID", "ID MAIN ==> " + user_id);


        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quizActivity(user_id);
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themeActivity(user_id);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authActivity();
            }
        });

    }

    private void quizActivity(int user_id) {
        Intent switch_page = new Intent(this, QuizActivity.class);
        switch_page.putExtra("userId", user_id);
        startActivity(switch_page);
    }

    private void authActivity() {
        Intent switch_page = new Intent(this, AuthActivity.class);
        startActivity(switch_page);
    }

    private void themeActivity(int user_id) {
        Intent switch_page = new Intent(this, ThemeActivity.class);
        switch_page.putExtra("userID", user_id);
        startActivity(switch_page);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}