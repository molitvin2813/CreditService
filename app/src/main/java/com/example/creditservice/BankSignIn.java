package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;


public class BankSignIn extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    EditText loginBank;
    EditText passwordBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_sign_in);

        loginBank = (EditText) findViewById(R.id.editTextLoginBank);
        passwordBank = (EditText) findViewById(R.id.editTextPasswordBank);
    }

    /**
     * Метод для входа в систему представителя банка
     * @param view авлпт
     */
    public void btnBankSignInClick(View view){
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

        Cursor cursor = mDb.rawQuery("SELECT * FROM t_bank WHERE t_bank.login='"+loginBank.getText().toString() + "' ", null);
        cursor.moveToFirst();

        if (!cursor.isAfterLast())
            if(cursor.getString(2).equals(passwordBank.getText().toString()))
                loginBank.setText("Вход выполнен");

        cursor.close();
        mDBHelper.close();
    }

    public void bankRegistration(View view){
        Intent intent = new Intent(this,RegistrationBankActivity.class);
        startActivity(intent);
    }

}
