package com.example.creditservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainUserActivity extends AppCompatActivity  {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    ListView list;
    String[] creditList= new String[10];
    private List<State> states = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        list = (ListView) findViewById(R.id.listViewMainUserActivity);


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


        Cursor cursor = mDb.rawQuery("SELECT t_credit.percent, t_credit.min_amount, t_credit.max_amount, t_bank.image " +
                "FROM t_credit " +
                "INNER JOIN t_bank " +
                "ON t_credit.t_bank_idt_bank=t_bank.idt_bank ", null);

        cursor.moveToFirst();

        Bitmap myBitmap;
        File imgFile;
        for(int i=0; i<9 && !cursor.isAfterLast(); i++){

            String s = cursor.getInt(0) + "% "+cursor.getInt(1) + " - " + cursor.getInt(2);
            //процент кредита
            states.add(new State (cursor.getInt(0) + "% ",cursor.getInt(1) + " - " + cursor.getInt(2), getResources().getIdentifier(cursor.getString(3), "drawable", getPackageName())));

            cursor.moveToNext();
        }
        StateAdapter stateAdapter = new StateAdapter(this, R.layout.list_item, states);
        // устанавливаем адаптер
        list.setAdapter(stateAdapter);
    }// onCreate

    /**
     * Метод для кнопки поиска.
     * При нажатии начинается поиск по безе.
     * @param view ввав
     */
    public void btnSearchOnClick(View view) {

    }// btnSearchOnClick



}
