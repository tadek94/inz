package com.example.tadziu.forrunners1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    boolean bieganie;
    boolean rower;
    Button przyciskBieganie;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        przyciskBieganie = (Button) findViewById(R.id.butBieganie);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            context = getApplicationContext();
            Intent intent = new Intent(context, MapsActivity.class);
            startActivity(intent);
        }
    };
        przyciskBieganie.setOnClickListener(listener);

    }
}
