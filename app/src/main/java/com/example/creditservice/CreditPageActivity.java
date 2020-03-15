package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class CreditPageActivity extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    private TextView nameBank;
    private TextView percent;
    private TextView amount;
    private TextView aboutBank;

    private ImageView bankImage;

    public static int idCredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_page);

        Intent intent = getIntent();
        idCredit = intent.getIntExtra("idCredit",-1);

        nameBank = (TextView) findViewById(R.id.textViewNameBankCreditActivity);
        percent = (TextView) findViewById(R.id.textViewPercentCreditActivity);
        amount = (TextView) findViewById(R.id.textViewAmountCreditActivity);
        aboutBank = (TextView) findViewById(R.id.textViewAboutBankTextCreditActivity);

        bankImage = (ImageView) findViewById(R.id.imageViewBankCreditActivity);


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
                "SELECT t_bank.image,\n" +
                "       t_bank.name,\n" +
                "       t_bank.about_bank_text,\n" +
                "       t_credit.min_amount,\n" +
                "       t_credit.max_amount,\n" +
                "       t_credit.percent\n" +
                "  FROM t_credit\n" +
                "       INNER JOIN\n" +
                "       t_bank ON t_credit.t_bank_idt_bank = t_bank.idt_bank\n" +
                " WHERE t_credit.idt_credit ="+ idCredit, null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()) {
            bankImage.setImageResource(getResources().getIdentifier(cursor.getString(0), "drawable", getPackageName()));
            nameBank.setText(cursor.getString(1));
            aboutBank.setText(cursor.getString(2));
            amount.setText("мин: " + cursor.getString(3) + "- макс: " + cursor.getString(4));
            percent.setText("Процент кредита: "+cursor.getString(5));
        }
    }

    public void showBranch(View view){

    }

    public void openSendMessageActivity(View view){
        Intent intent = new Intent(this, SendMessageActivity.class);
        startActivity(intent);
    }
}
