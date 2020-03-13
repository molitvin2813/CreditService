package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainBankActivity extends AppCompatActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    private ListView list;
    private List<Message> messageList;

    private int page=0;
    private int idBank;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_bank);

        list = (ListView) findViewById(R.id.listViewMainBankActivity);
        Intent intent = getIntent();
        idBank = intent.getIntExtra("id",-1);
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


        Cursor cursor = mDb.rawQuery(
                "SELECT t_message.header," +
                "       t_message.approved," +
                "       t_bank.name," +
                "       t_user.login" +
                "  FROM t_message" +
                "       INNER JOIN" +
                "       t_bank ON t_message.t_bank_idt_bank = t_bank.idt_bank" +
                "       INNER JOIN" +
                "       t_user ON t_message.t_user_idt_user = t_user.idt_user" +
                " WHERE t_message.t_bank_idt_bank = "+idBank, null);


        cursor.moveToPosition(0+page*10);
        messageList = new ArrayList();
        for(int i=0; i<9 && !cursor.isAfterLast(); i++){
            messageList.add(new Message(cursor.getString(0),
                    Boolean.parseBoolean( cursor.getInt(1)+""),
                    cursor.getString(2),
                    cursor.getString(3)));
            cursor.moveToNext();
        }
        MessageAdapter messageAdapter = new MessageAdapter(this, R.layout.list_item_for_message, messageList);
        // устанавливаем адаптер
        list.setAdapter(messageAdapter);

        cursor.close();
        mDBHelper.close();
    }

    /**
     * Метод для кнопки предыдущая страница
     * Показывает предыдущие 10 кредитов из таблицы
     * @param view ыва
     */
    public void prevPage(View view){
        if(page==0)
            return;
        page--;
        makeRequestToDB();
    }
    public void nextPage(View view){
        page++;
        makeRequestToDB();
    }
}
