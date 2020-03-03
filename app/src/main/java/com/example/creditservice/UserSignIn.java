package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

public class UserSignIn extends AppCompatActivity {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    EditText loginUser;
    EditText passwordUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_in);
        loginUser = (EditText) findViewById(R.id.editTextLoginUser);
        passwordUser = (EditText) findViewById(R.id.editTextPasswordUser);

    }

    /**
     * Метод для входа обычного пользователя системы
     * @param view выа
     */
    public void btnUserSignInClick(View view){
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

        Cursor cursor = mDb.rawQuery("SELECT * FROM t_user WHERE t_user.login = '"+loginUser.getText().toString() + "' ", null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast())
            if(cursor.getString(2).equals(passwordUser.getText().toString()))
                loginUser.setText("Вход выполнен");

        cursor.close();
        mDBHelper.close();
    }
}
