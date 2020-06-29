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

import java.io.IOException;

import network.Api;
import network.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthActivity extends AppCompatActivity {

    private TextView title;
    private EditText pseudo;
    private EditText password;
    private Integer id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        this.title = findViewById(R.id.auth_title);
        this.pseudo = findViewById(R.id.auth_pseudo);
        this.password = findViewById(R.id.auth_password);
        final Button connexion = findViewById(R.id.auth_connexion);
        final Button inscription = findViewById(R.id.auth_inscription);

        inscription.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                inscriptionActivity();
            }
        });

        connexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user = new User(
                        id,
                        pseudo.getText().toString().trim(),
                        password.getText().toString().trim()
                );

                loggingUser(user);
            }
        });

    }

    private void inscriptionActivity(){
        Intent switch_page = new Intent(this, InscriptionActivity.class);
        startActivity(switch_page);
    }

    private void refreshActivity(){
        Intent refresh = new Intent(this, AuthActivity.class);
        startActivity(refresh);
    }

    private void mainActivity(int id){
        Intent switch_page = new Intent(this, MainActivity.class);
        switch_page.putExtra("userId", id);
        startActivity(switch_page);
        Log.i("ID", "ID OK =>  " +  id );
    }

    private void loggingUser(final User user){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<User> call = api.auth(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AuthActivity.this, "Welcome !!", Toast.LENGTH_SHORT).show();
                    id = response.body().getId();
                   // Log.i("ID", "ID OK =>  " +  id );
                    mainActivity(id);
                } else {
                    Toast.makeText(AuthActivity.this, "ERROR Login/Password", Toast.LENGTH_SHORT).show();
                    refreshActivity();
                }
                pseudo.setText("");
                password.setText("");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(AuthActivity.this, "ERROR => KO", Toast.LENGTH_SHORT).show();
            }
        });

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
