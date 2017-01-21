package com.example.tadziu.forrunners1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TreningiActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treningi);
    }

    public void onClickIntervaly(View view)
    {
        Intent intent = new Intent(this, IntervalyAct.class);
        startActivity(intent);
    }
    public void onClickTabKal(View view)
    {
        Intent intent = new Intent(this, TabKaloriiActivity.class);
        startActivity(intent);
    }

}
