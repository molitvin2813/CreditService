package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class RegistrationUserActivity extends AppCompatActivity {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private EditText login;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_user);

        login = (EditText) findViewById(R.id.editTextLoginRegistration);
        password = (EditText) findViewById(R.id.editTextPasswordRegistration);
    }// onCreate

    /**
     * Обработчик для кнопки регистрации пользователя в системе
     * @param view фыва
     */
    public void btnRegistrationClick(View view){
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

        mDb.execSQL("INSERT INTO t_user (login, password) VALUES ('44', '444');");
        mDBHelper.close();
        Intent intent = new Intent(this, ProfileFillActivity.class);
        intent.putExtra("login",  login.getText().toString());
        startActivity(intent);
    }// btnRegistrationClick
}
