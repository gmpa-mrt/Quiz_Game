package com.example.game_learning;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import network.Api;
import network.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InscriptionActivity extends AppCompatActivity {

    private TextView title;
    private EditText new_pseudo = null;
    private EditText new_password = null;
    private Integer id = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        this.title = findViewById(R.id.inscription_title);
        this.new_pseudo = findViewById(R.id.inscription_pseudo);
        this.new_password = findViewById(R.id.inscription_password);
        final Button validation = findViewById(R.id.inscription_validation);
        final Button back = findViewById(R.id.inscription_back);

        validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User new_user = new User(
                        id,
                        new_pseudo.getText().toString().trim(),
                        new_password.getText().toString().trim()
                );
                if (!new_user.getPassword().isEmpty() && !new_user.getPassword().equals("")) {
                    postUser(new_user);
                    authActivity();
                }else {
                    Toast.makeText(InscriptionActivity.this, "Please !!!!!! Don't do this", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authActivity();
            }
        });
    }

    private void authActivity() {
        Intent switch_page = new Intent(this, AuthActivity.class);
        startActivity(switch_page);
    }

    private void refreshActivity() {
        Intent refresh = new Intent(this, InscriptionActivity.class);
        startActivity(refresh);
    }

    private void postUser(final User user) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<User> call = api.createUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(InscriptionActivity.this, "OK => User ID: " + response.body().getId(), Toast.LENGTH_SHORT).show();
                    Log.d("DEBUG", "OK => response = " + response.message());
                    Log.d("DEBUG", "OK => response = " + response.body());
                } else {
                    Toast.makeText(InscriptionActivity.this, "Wrong pseudo/password", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(InscriptionActivity.this, "ERROR => KO", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
