package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileFillActivity extends AppCompatActivity {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private Spinner spinner;

    private EditText creditAmount;
    private EditText eMail;
    private EditText telephoneNumber;
    private String login;

    String[] creditList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fill);

        creditAmount = (EditText) findViewById(R.id.editTextCreditAmountProfileFillActivity);
        eMail = (EditText) findViewById(R.id.editTextEMailProfileFillActivity);
        telephoneNumber = (EditText) findViewById(R.id.editTextTelephoneNumberProfileFillActivity);

        Intent intent = getIntent();
        login = intent.getStringExtra("login");

        /**
         * заполняем спинер занчениеями из таблицы t_city
         */
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

        Cursor cursor = mDb.rawQuery("SELECT * FROM t_city", null);
        cursor.moveToFirst();

        creditList= new String[cursor.getCount()];
        for(int i=0;!cursor.isAfterLast(); i++){
            creditList[i] = String.valueOf(cursor.getInt(0));
            cursor.moveToNext();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, creditList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner = (Spinner) findViewById(R.id.spinnerCityProfileFillActivity);
        spinner.setAdapter(adapter);
        // заголовок
        spinner.setPrompt("Город");
        // выделяем элемент
        spinner.setSelection(0);

        cursor.close();
        mDBHelper.close();
    } // onCreate

    /**
     * Обработчик для кнопки заполнения профиля пользователя необходимой информфацией
     * @param view ыва
     */
    public void btnOkFillActivity(View view){

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

        mDb.execSQL("INSERT INTO t_profile (credit_amount, email, telephone_number, t_user_idt_user, t_city_idt_city) " +
                "VALUES ("+Integer.parseInt(creditAmount.getText().toString())+", '"+eMail.getText().toString()+"', " +
                " '" +telephoneNumber.getText().toString()+"', "+getIdUser()+", "+spinner.getSelectedItem().toString()+" )");

        mDBHelper.close();
        finish();
    }// btnOkFillActivity

    /**
     * Метод, который возвращает id пользователя с заданным логином
     * @return int ID пользователя
     */
    private int getIdUser(){
        int id=-1;
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

        Cursor cursor = mDb.rawQuery("SELECT t_user.idt_user FROM t_user WHERE t_user.login = '"+login+"' ", null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast())
            id = cursor.getInt(0);

        return id;
    }
}
