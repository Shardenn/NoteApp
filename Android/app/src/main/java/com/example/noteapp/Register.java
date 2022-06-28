package com.example.noteapp;

import com.example.noteapp.api.RetrofitClient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import retrofit2.Call;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText firstNameText, lastNameText, emailAddressText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstNameText = findViewById(R.id.editTxt_firstName);
        lastNameText = findViewById(R.id.editTxt_lastName);
        emailAddressText = findViewById(R.id.editTxt_emailRegister);
        passwordText = findViewById(R.id.editTxt_passwordRegister);

        findViewById(R.id.btn_userRegister).setOnClickListener(this);
        findViewById(R.id.btn_registerBack).setOnClickListener(this);
    }

    private void registerUser() {
        String firstName = firstNameText.getText().toString().trim();
        String lastName = lastNameText.getText().toString().trim();
        String emailAddress = emailAddressText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getAPI()
                .createUser(firstName, lastName, emailAddress, password);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String body = response.body().string();
                    Toast.makeText(Register.this, body, Toast.LENGTH_LONG).show();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Register.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void goBack() {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registerBack:
                goBack();
                break;
            case R.id.btn_userRegister:
                registerUser();
                break;
        }
    }
}
