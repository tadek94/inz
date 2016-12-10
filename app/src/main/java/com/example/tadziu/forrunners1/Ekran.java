package com.example.tadziu.forrunners1;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

/**
 * Created by TADZIU on 2016-12-10.
 */

public class Ekran {

    Bitmap zdjEkranu;



    public Bitmap getScreenShot(View view) {
//        View screenView = view.getRootView();
//        screenView.setDrawingCacheEnabled(true);
//        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
//        screenView.setDrawingCacheEnabled(false);
//        return bitmap;

        zdjEkranu = null;

        try {


            // pobranie szerokości i wysokości ekranu
            int width = view.getWidth();
            int height = view.getHeight();
            zdjEkranu = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

            //canvas z bitmapy
            Canvas canvas = new Canvas(zdjEkranu);
            view.draw(canvas);


        } catch (Exception e) {

            e.printStackTrace();
        }

        return zdjEkranu;

    }


}
