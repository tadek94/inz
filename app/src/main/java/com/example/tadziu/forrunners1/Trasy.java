package com.example.tadziu.forrunners1;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class Trasy extends ListActivity { // AppCompatActivity

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasy);
        ListView listaTras = getListView();

        try {
            SQLiteOpenHelper baseManager = new BaseManager(this);

            db = baseManager.getReadableDatabase(); // ref do bazy danych
            cursor = db.query("MOJETRASY",new String[]{"_id","OPIS"},null,null,null,null,null);

            CursorAdapter listAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1,
                    cursor,new String[]{"OPIS"},new int[]{android.R.id.text1},0);
            listaTras.setAdapter(listAdapter);

        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this, "Baza danych jest niedostepna",Toast.LENGTH_SHORT);
            toast.show();
        }



    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id)
    {
        Intent intent = new Intent(Trasy.this, Trasa.class);
        intent.putExtra(Trasa.TRASA_NUM, (int)id);
        startActivity(intent);
    }






}
