package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class MessageActivity extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private TextView nameBank;
    private TextView percent;
    private TextView amount;
    private TextView userName;
    private TextView eMail;
    private TextView header;
    private TextView messageText;

    private ImageView bankImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        nameBank = (TextView) findViewById(R.id.textViewNameBankMessageActivity);
        percent = (TextView) findViewById(R.id.textViewPercentMessageActivity);
        amount = (TextView) findViewById(R.id.textViewAmountMessageActivity);

        userName = (TextView) findViewById(R.id.textViewUserNameMessageActivity);
        eMail = (TextView) findViewById(R.id.textViewEMailMessageActivity);
        header = (TextView) findViewById(R.id.textViewHeaderMessageActivity);
        messageText = (TextView) findViewById(R.id.textViewMesMessageActivity);

        bankImage = (ImageView) findViewById(R.id.imageViewBankMessageActivity);

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

        Cursor cursor = mDb.rawQuery(
                "SELECT t_message.header,\n" +
                        "       t_message.approved,\n" +
                        "       t_bank.image,\n" +
                        "       t_bank.name,\n" +
                        "       t_credit.min_amount,\n" +
                        "       t_credit.max_amount,\n" +
                        "       t_credit.percent,\n" +
                        "       t_message.text," +
                        "       t_user.login"+
                        "  FROM t_message\n" +
                        "       INNER JOIN\n" +
                        "       t_bank ON t_message.t_bank_idt_bank = t_bank.idt_bank\n" +
                        "       INNER JOIN\n" +
                        "       t_user ON t_message.t_user_idt_user = t_user.idt_user\n" +
                        "       INNER JOIN\n" +
                        "       t_credit ON t_message.id_credit = t_credit.idt_credit\n" +
                        " WHERE t_message.idt_message =" +MainBankActivity.idMessage, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            header.setText("Заголовок: "+cursor.getString(0));
            bankImage.setImageResource(getResources().getIdentifier(cursor.getString(2), "drawable", getPackageName()));
            nameBank.setText(cursor.getString(3));
            amount.setText("мин: " + cursor.getString(4) + "- макс: " + cursor.getString(5));
            percent.setText("Процент кредита: "+cursor.getString(6));
            messageText.setText(cursor.getString(7));
            userName.setText("От пользователя: "+cursor.getString(8));
        }
        cursor.close();
        mDBHelper.close();
    }

    public void approvedMessage(View view){
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
        mDb.execSQL(" UPDATE t_message\n" +
                "        SET approved = 1" +
                "        WHERE idt_message ="+MainBankActivity.idMessage);
        mDBHelper.close();
        finish();
    }

}
