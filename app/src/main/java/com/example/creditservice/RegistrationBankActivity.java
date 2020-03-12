package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class RegistrationBankActivity extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private EditText login;
    private EditText password;
    private EditText aboutBank;
    private EditText name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_bank);

        login = (EditText) findViewById(R.id.editTextLoginBankRegistration);
        password = (EditText) findViewById(R.id.editTextLoginBankRegistration);
        aboutBank = (EditText) findViewById(R.id.editTextAboutBankRegistration);
        name = (EditText) findViewById(R.id.editTextNameBankRegistration);

    }

    public void btnBankRegistrationClick(View view){
        mDBHelper = new DataBaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        mDb.execSQL("INSERT INTO t_bank (login, password, about_bank_text, image, name) " +
                "VALUES ('"+login.getText().toString()+"', '"+password.getText().toString()+"', " +
                "'"+aboutBank.getText().toString()+"', 'plant', '"+name.getText().toString()+"'  )");
        mDBHelper.close();
        finish();
    }
}
