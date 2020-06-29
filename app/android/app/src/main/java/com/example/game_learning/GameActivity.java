package com.example.game_learning;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import network.Answer;
import network.Api;
import network.Question;
import network.Quiz;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GameActivity extends AppCompatActivity {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    Api api = retrofit.create(Api.class);


    Integer themeId;
    Integer userId;
    Integer quizId;
    Integer count = 0;
    Integer indexAnswer = 0;
    Integer score = 0;

    private TextView view_question;
    private TextView number_question;
    private TextView game_timer;
    private TextView game_score;
    private Button confirm;
    private RadioGroup group_radio;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private RadioButton radio4;

    List<Question> questionList = new ArrayList<>();
    List<Answer> answerList = new ArrayList<>();
    List<Answer> finalList = new ArrayList<>();
    List<Answer> final_score = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        confirm = findViewById(R.id.game_btn_confirm);
        view_question = findViewById(R.id.game_view_question);
        number_question = findViewById(R.id.game_number_question);
        game_timer = findViewById(R.id.game_timer);
        group_radio = findViewById(R.id.game_group_radio);
        group_radio.setVisibility(View.INVISIBLE);
        radio1 = findViewById(R.id.game_btn_radio1);
        radio2 = findViewById(R.id.game_btn_radio2);
        radio3 = findViewById(R.id.game_btn_radio3);
        radio4 = findViewById(R.id.game_btn_radio4);

        Bundle bundleTheme = getIntent().getExtras();
        assert bundleTheme != null;
        themeId = bundleTheme.getInt("themeId");

        Bundle bundleUser = getIntent().getExtras();
        assert bundleUser != null;
        userId = bundleUser.getInt("userId");

        Bundle bundleQuiz = getIntent().getExtras();
        assert bundleQuiz != null;
        quizId = bundleUser.getInt("quizId");

        getQuestions(themeId);
        getAnswer();
        eventClick();


        Log.i("QUIZ", "ID GAME    =====> " + quizId);
        Log.i("QUIZ", "ID USER   =====> " + userId);
        Log.i("QUIZ", "ID THEME  =====> " + themeId);

    }

    private void eventClick() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count > questionList.size()) {
                    mainActivity(userId);
                } else if (count == questionList.size()) {
                    displayQuestion(count);
                    displayNumberQuestion(questionList);
                    count++;
                    game_timer.setVisibility(View.INVISIBLE);
                } else {
                    displayQuestion(count);
                    displayNumberQuestion(questionList);
                    displayAnswer();
                    setScore();
                    startTimer();
                    count++;
                    indexAnswer += 4;
                }

                for (int i = 0; i < group_radio.getChildCount(); i++) {
                    group_radio.getChildAt(i).setEnabled(true);
                }

                //Log.i("COUNT", "OK ======> " + count);
               // Log.i("RADIO", "Final Score   ======> " + final_score);
            }
        });
    }

    private void setScore() {

        group_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

                for (int i = 0; i < group_radio.getChildCount(); i++) {
                    group_radio.getChildAt(i).setEnabled(false);
                }

                for (int i = 0; i < finalList.size(); i++) {
                    if (radioButton.getText().equals(finalList.get(i).getText())) {
                        if (!final_score.contains(finalList.get(i))) {
                            final_score.add(finalList.get(i));
                            score += finalList.get(i).getCorrect();
                        }
                    }
                }
                game_score = findViewById(R.id.game_score);
                game_score.setText("Score: "+score);
                Quiz quizUpdated = new Quiz(themeId, userId, score);
                updateQuiz(quizUpdated);
                Log.i("QUIZ", "ID SCORE   =====> " + score);
            }

        });
    }
//@TODO     Renseignement sur la methode Patch et field etc ...
    private void updateQuiz(Quiz quiz) {
        Call<Quiz> call = api.updateQuiz(quizId, quiz);
        Log.i("QUIZ", "ID SCORE  IN UPDATE  =====> " + score);
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                Log.i("QUIZ", "SUCCESSFUL  =====> " + response.message());
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {
                Log.i("QUIZ", "ON FAILURE  =====> " + t.getMessage());
            }
        });
    }

    private void mainActivity(int id) {
        Intent switch_page = new Intent(this, MainActivity.class);
        switch_page.putExtra("userId", id);
        startActivity(switch_page);
        //Log.i("ID", "ID OK =>  " + id);
    }

    private void getQuestions(final int themeId) {
        Call<List<Question>> call = api.getQuestion();
        call.enqueue(new Callback<List<Question>>() {
            @Override
            public void onResponse(Call<List<Question>> call, Response<List<Question>> response) {
                if (!response.isSuccessful()) {
                    Log.d("DEBUG", "getQuestions doesn't work" + response.errorBody());
                    return;
                }
                assert response.body() != null;
                for (int i = 0; i < response.body().size(); i++) {
                    if (themeId == response.body().get(i).getTheme_id()) {
                        questionList.add(response.body().get(i));
                       // Log.i("QUESTION", "TEST QUESTIONS  =====> " + questionList);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Question>> call, Throwable t) {
            }
        });
    }

    private void getAnswer() {
        Call<List<Answer>> call = api.getAnswerList();
        call.enqueue(new Callback<List<Answer>>() {
            @Override
            public void onResponse(Call<List<Answer>> call, Response<List<Answer>> response) {
                if (!response.isSuccessful()) {
                    // Log.d("DEBUG", "getQuestions doesn't work" + response.errorBody());
                    return;
                }
                for (int i = 0; i < response.body().size(); i++) {
                    answerList.add(response.body().get(i));
                    if (answerList.get(i).getTheme_id() == themeId) {
                        finalList.add(answerList.get(i));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Answer>> call, Throwable t) {

            }
        });
    }

    private void displayQuestion(final int count) {

        if (count > this.questionList.size() - 1) {
            view_question.setText("Finish !!");
            group_radio.setVisibility(View.INVISIBLE);
        } else {
            group_radio.setVisibility(View.VISIBLE);
            view_question.setText(this.questionList.get(count).getWording());
        }
    }

    private void displayAnswer() {

        if (finalList.get(indexAnswer).getQuestion_id() == questionList.get(count).getId()) {
            radio1.setChecked(false);
            radio2.setChecked(false);
            radio3.setChecked(false);
            radio4.setChecked(false);
            radio1.setText(finalList.get(indexAnswer).getText());
            radio2.setText(finalList.get(indexAnswer + 1).getText());
            radio3.setText(finalList.get(indexAnswer + 2).getText());
            radio4.setText(finalList.get(indexAnswer + 3).getText());
        }
    }

    private void displayNumberQuestion(List<Question> questionList) {

        if (count + 1 > questionList.size()) {
            number_question.setText("Qestion:" + questionList.size() + "/" + questionList.size());
            confirm.setText("Finish");
        } else {
            number_question.setText("Question:" + (count + 1) + "/" + (questionList.size()));
        }
    }

    private void startTimer() {

        new CountDownTimer(30000, 1) {

            public void onTick(long millisUntilFinished) {
                game_timer.setText("00:" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                group_radio.setVisibility(View.INVISIBLE);
            }

        }.start();
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
