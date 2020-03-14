package com.example.creditservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainUserActivity extends AppCompatActivity  {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    ListView list;
    EditText searchField;
    private TextView nameBank;
    private List<Credit> states = new ArrayList();
    private int page=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        list = (ListView) findViewById(R.id.listViewMainUserActivity);
        searchField = (EditText) findViewById(R.id.editTextSearch) ;

        mDBHelper = new DataBaseHelper(this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nameBank = view.findViewById(R.id.textViewID);
                Intent intent = new Intent(MainUserActivity.this, CreditPageActivity.class);
                intent.putExtra("idCredit",Integer.parseInt(nameBank.getText().toString()));
                startActivity(intent);
            }
        });


        makeRequestToDB();
    }// onCreate

    /**
     * Метод для кнопки поиска.
     * При нажатии начинается поиск по безе.
     * @param view ввав
     */
    public void btnSearchOnClick(View view) {
        Intent searchIntent = new Intent(this,SearchActivity.class);
        searchIntent.putExtra("request", searchField.getText().toString());
        startActivity(searchIntent);
    }// btnSearchOnClick

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

    /**
     * Выполнение запроса на поиск всех кредитов на определенной странице
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


        Cursor cursor = mDb.rawQuery("SELECT t_credit.percent, t_credit.min_amount, t_credit.max_amount, t_bank.image, t_bank.name, t_credit.idt_credit " +
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
                    cursor.getString(4), cursor.getInt(5)));

            cursor.moveToNext();
        }
        CreditAdapter stateAdapter = new CreditAdapter(this, R.layout.list_item, states);
        // устанавливаем адаптер
        list.setAdapter(stateAdapter);

        cursor.close();
        mDBHelper.close();
    }

    /**
     * Метод для кнопки следующая страница
     * Показывает следующие 10 кредитов
     * @param view ыва
     */
    public void nextPage(View view){
        page++;
        makeRequestToDB();
    }


}
