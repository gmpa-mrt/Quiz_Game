package com.example.game_learning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import network.Api;
import network.Quiz;
import network.Theme;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private List<Theme> themeList = new ArrayList<>();
    private List<Integer> themeListId = new ArrayList<>();
    private Integer user_id;
    private Integer quizId;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    Api api = retrofit.create(Api.class);


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_quiz);

        Bundle bundle = getIntent().getExtras();
        user_id = bundle.getInt("userId");

        inputThem();
        spinner.setOnItemSelectedListener(this);
    }

    private void inputThem() {
        spinner = findViewById(R.id.quiz_spinner);

        Call<List<Theme>> call = api.getTheme();
        call.enqueue(new Callback<List<Theme>>() {
            @Override
            public void onResponse(Call<List<Theme>> call, Response<List<Theme>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(QuizActivity.this, "Error !! It's not successful", Toast.LENGTH_SHORT).show();
                }
                assert response.body() != null;
                for (int i = 0; i < response.body().size(); i++) {
                    Theme th = response.body().get(i);
                    themeList.add(th);
                    themeListId.add(th.getId());
                }
                final ArrayAdapter<Theme> adapter = new ArrayAdapter<Theme>(QuizActivity.this, android.R.layout.simple_spinner_dropdown_item, themeList);
                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                adapter.notifyDataSetChanged();
                spinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Theme>> call, Throwable t) {
                Toast.makeText(QuizActivity.this, "Request failed ! " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void startQuiz(final int userId, final int themeId) {
        final Quiz quiz = new Quiz(themeId, userId, 0);

        Call<Quiz> call = api.postQuiz(quiz);
        call.enqueue(new Callback<Quiz>() {
            @Override
            public void onResponse(Call<Quiz> call, Response<Quiz> response) {
                Toast.makeText(QuizActivity.this, "Hello World ! ", Toast.LENGTH_SHORT).show();
                quizId = response.body().getId();
                gameActivity(themeId, userId, quizId);
            }

            @Override
            public void onFailure(Call<Quiz> call, Throwable t) {

            }
        });
    }

    private void gameActivity(int themeId, int userId, int quizId) {
        Log.i("QUIZ", "ID88888    =====> " + quizId);
        Intent switch_page = new Intent(this, GameActivity.class);
        switch_page.putExtra("themeId", themeId);
        switch_page.putExtra("userId", userId);
        switch_page.putExtra("quizId", quizId);
        startActivity(switch_page);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final Button start = findViewById(R.id.quiz_btn_start);
        Toast.makeText(this, parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
        final Integer themeId = themeListId.get(position);
        final Integer userId = this.user_id;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz(themeId, userId);
                //gameActivity(themeId, userId);
            }
        });

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
    protected void onRestart() {
        super.onRestart();
    }
}

