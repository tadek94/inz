package com.example.tadziu.forrunners1;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TADZIU on 2016-12-06.
 */

public class BaseManager extends SQLiteOpenHelper {

    private static final String DataBase_Name = "Trasy";
    private static final int DataBase_Version = 2;

    BaseManager(Context context) {
        super(context, DataBase_Name, null, DataBase_Version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE MOJETRASY (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "OPIS TEXT, "
                + "ODLEGLOSC REAL, "
                + "PREDKOSC REAL, "
                + "ZDJECIE BLOB);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {



    }

    public void dodajTrase(String opis, double odleglosc, double predkosc, byte[] zdj)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues wartosci = new ContentValues();
        wartosci.put("OPIS", opis);
        wartosci.put("ODLEGLOSC", odleglosc);
        wartosci.put("PREDKOSC", predkosc);
        wartosci.put("ZDJECIE", zdj);
        db.insert("MOJETRASY", null, wartosci);

    }






}
