package com.example.tadziu.forrunners1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Trasa extends AppCompatActivity {

    public static final String TRASA_NUM = "num";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trasa);

        int trasaNum = (Integer)getIntent().getExtras().get(TRASA_NUM);

        try {
            SQLiteOpenHelper baseManager = new BaseManager(this);
            SQLiteDatabase db = baseManager.getReadableDatabase();
            Cursor cursor = db.query("MOJETRASY",
                    new String[] {"OPIS" , "ODLEGLOSC" , "PREDKOSC" , "ZDJECIE"},
                    "_id = ?",
                    new String[] {Integer.toString(trasaNum)},null,null,null);

            if (cursor.moveToFirst())
            {
                String opisB = cursor.getString(0);
                //int odlegloscB = cursor.getInt(1);
                float odlegloscB = cursor.getFloat(1);
                float predkoscB = cursor.getFloat(2);
                byte[] zdjB = cursor.getBlob(3);

                TextView opis = (TextView)findViewById(R.id.opis);
                opis.setText(opisB);

                TextView odleglosc = (TextView)findViewById(R.id.odleglosc);
                odleglosc.setText("Odleglosc: "+Float.toString(odlegloscB).substring(0,5));

                TextView predkosc = (TextView)findViewById(R.id.predkosc);
                predkosc.setText("Predkosc: "+Float.toString(predkoscB).substring(0,5));


                Bitmap bmp = BitmapFactory.decodeByteArray(zdjB, 0, zdjB.length);
                ImageView imageView = (ImageView)findViewById(R.id.photo);
                imageView.setImageBitmap(bmp);


               // TextView zdj = (TextView)findViewById(R.id.zdj);
               // zdj.setText(zdjB.toString());




            }
            cursor.close();
            db.close();



        }catch (SQLiteException e){
        Toast toast = Toast.makeText(this, "Blad bazy danych", Toast.LENGTH_SHORT);
        toast.show();}

    }
}
