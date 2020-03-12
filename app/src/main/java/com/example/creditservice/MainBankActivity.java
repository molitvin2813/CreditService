package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainBankActivity extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ListView list;
    private List<Credit> states = new ArrayList();

    private int page=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bank);

        list = (ListView) findViewById(R.id.listViewMainBankActivity);

        makeRequestToDB();
    }

    /**
     * Выполнение запроса на поиск всех сообщений для данного банка
     */
    private void makeRequestToDB(){
        //Читаем данные из таблицы
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


        Cursor cursor = mDb.rawQuery("SELECT t_credit.percent, t_credit.min_amount, t_credit.max_amount, t_bank.image, t_bank.name " +
                "FROM t_credit " +
                "INNER JOIN t_bank " +
                "ON t_credit.t_bank_idt_bank=t_bank.idt_bank ", null);


        cursor.moveToPosition(0+page*10);
        states = new ArrayList();
        for(int i=0; i<9 && !cursor.isAfterLast(); i++){

            String s = cursor.getInt(0) + "% "+cursor.getInt(1) + " - " + cursor.getInt(2);
            //процент кредита
            states.add(new Credit(cursor.getInt(0) + "% ",
                    cursor.getInt(1) + " - " + cursor.getInt(2),
                    getResources().getIdentifier(cursor.getString(3), "drawable", getPackageName()),
                    cursor.getString(4)));

            cursor.moveToNext();
        }
        CreditAdapter stateAdapter = new CreditAdapter(this, R.layout.list_item, states);
        // устанавливаем адаптер
        list.setAdapter(stateAdapter);

        cursor.close();
        mDBHelper.close();
    }
}
