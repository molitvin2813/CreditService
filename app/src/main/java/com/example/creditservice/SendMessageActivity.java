package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

public class SendMessageActivity extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private EditText header;
    private EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);


        header = (EditText) findViewById(R.id.editTextHeaderSendMessageActivity);
        text = (EditText) findViewById(R.id.editTextMesSendMessageActivity);

    }

    /**
     * Обработчик для кнопки отправить сообщение
     * @param view ывфа
     */
    public void sendMessage(View view){
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


        mDb.execSQL("INSERT INTO t_message (header, text, approved, t_bank_idt_bank, t_user_idt_user, id_credit) " +
                "VALUES ('"+header.getText().toString()+"', '"+text.getText().toString()+"', 0,"+MainUserActivity.idBank+", "+MainUserActivity.idUser+", "+CreditPageActivity.idCredit+")");

        Toast toast = Toast.makeText(getApplicationContext(),
                "Сообщение отправлено", Toast.LENGTH_SHORT);
        toast.show();

        mDBHelper.close();
        finish();
    }
}
