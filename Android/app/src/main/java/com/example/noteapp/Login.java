package com.example.noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.noteapp.api.RetrofitClient;
import com.example.noteapp.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private EditText emailAddressText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailAddressText = findViewById(R.id.editTxt_emailLogin);
        passwordText = findViewById(R.id.editTxt_passwordLogin);

        findViewById(R.id.btn_userLogin).setOnClickListener(this);
        findViewById(R.id.btn_loginBack).setOnClickListener(this);
    }

    private void userLogin() {
        String email = emailAddressText.getText().toString().trim();
        String pass = passwordText.getText().toString().trim();

        Call<LoginResponse> call = RetrofitClient
                .getInstance()
                .getAPI()
                .userLogin(email, pass);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();

                if (!loginResponse.isError()) {
                    writeUserIdToSharedPreferences(loginResponse.getUser().getId());
                    goToUserProfile(loginResponse.getUser().getId());
                } else {
                    Toast.makeText(Login.this, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goBack() {
        startActivity(new Intent(this, MainActivity.class));
    }
    private void goToUserProfile(String userId) {
        Intent userWS = new Intent(this, UserWorkspace.class);
        userWS.putExtra(getString(R.string.last_user_id), userId);
        startActivity(userWS);
    }

    private void writeUserIdToSharedPreferences(String userId) {
        SharedPreferences sharedPrefs = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.putString(getString(R.string.last_user_id), userId);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_userLogin:
                userLogin();
                break;
            case R.id.btn_loginBack:
                goBack();
                break;
        }    }
}
