package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    ListView listView;
    private List<Credit> states = new ArrayList();
    private EditText searchField;
    private String searchRequest;
    private int page=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        listView = (ListView) findViewById(R.id.listViewActivitySearch);
        Intent intent = getIntent();
        searchRequest = intent.getStringExtra("request");
        searchField = (EditText) findViewById(R.id.editTextSearchActivitySearch);
        //Читаем данные из таблицы
        makeRequestToDB();
    }

    private void makeRequestToDB(){
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

        states = new ArrayList();
        Cursor cursor = mDb.rawQuery("SELECT t_credit.percent, t_credit.min_amount, t_credit.max_amount, t_bank.image, t_bank.name t_credit.idt_credit" +
                "FROM t_credit " +
                "INNER JOIN t_bank " +
                "ON t_credit.t_bank_idt_bank=t_bank.idt_bank " +
                "WHERE t_bank.name= '"+searchRequest+ "';", null);

        cursor.moveToPosition(0+page*10);

        for(int i=0; i<9 && !cursor.isAfterLast(); i++){
            String s = cursor.getInt(0) + "% "+cursor.getInt(1) + " - " + cursor.getInt(2);
            //процент кредита
            states.add(new Credit(cursor.getInt(0) + "% ",
                    cursor.getInt(1) + " - " + cursor.getInt(2),
                    getResources().getIdentifier(cursor.getString(3), "drawable", getPackageName()),
                    cursor.getString(4), cursor.getInt(5)));
            cursor.moveToNext();
        }
        CreditAdapter adapter = new CreditAdapter(this, R.layout.list_item, states);
        listView.setAdapter(adapter);
        cursor.close();
        mDBHelper.close();
    }

    public void btnSearchOnClick(View view) {
        page = 0;
        searchRequest=searchField.getText().toString();
        makeRequestToDB();
    }

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
