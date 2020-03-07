package com.example.creditservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class MainUserActivity extends ListActivity {
    private DataBaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    ListView list;
    String[] creditList= new String[10];
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


        Cursor cursor = mDb.rawQuery("SELECT * FROM t_credit;", null);

        cursor.moveToFirst();


        for(int i=0; i<9 || !cursor.isAfterLast(); i++){
            //процент кредита
            creditList[i].concat(cursor.getInt(1)+"% ");
            // минимальная-максимальная сумма кредита
            creditList[i].concat(cursor.getInt(2)+" - " + cursor.getInt(3));

        }



        setListAdapter(new MyAdapter(this, R.layout.list_item, creditList));
    }// onCreate

    /**
     * Метод для кнопки поиска.
     * При нажатии начинается поиск по безе.
     * @param view ввав
     */
    public void btnSearchOnClick(View view) {

    }// btnSearchOnClick


    private class MyAdapter extends ArrayAdapter<String> {

        MyAdapter(Context context, int textViewResourceId, String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.list_item, parent, false);
            TextView label = (TextView) row.findViewById(R.id.text_view_cat_name);
            label.setText(creditList[position]);
            ImageView iconImageView = (ImageView) row.findViewById(R.id.image_view_icon);
            // Если текст содержит кота, то выводим значок Льва (лев - это кот!)
            if (creditList[position].equalsIgnoreCase("Лев")) {
                iconImageView.setImageResource(R.drawable.plant);
            } else {
                iconImageView.setImageResource(R.mipmap.ic_launcher);
            }
            return row;
        }
    }

}
