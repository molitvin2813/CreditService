package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void clickBtnUserSignIn(View view) {
        Intent intent = new Intent(this, UserSignIn.class);
        startActivity(intent);
    }

    public void clickBtnBankSingIn(View view) {
        Intent intent = new Intent(this, BankSignIn.class);
        startActivity(intent);
    }
}
