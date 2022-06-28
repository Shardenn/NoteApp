package com.example.noteapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_login).setOnClickListener(this);
    }

    private void userSignUp() {
        startActivity(new Intent(this, Register.class));
    }

    private void userLogin() {
        startActivity(new Intent(this, Login.class));
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_register:
                userSignUp();
                break;
            case R.id.btn_login:
                userLogin();
                break;
        }
    }
}
