package com.example.userdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(255, 255, 255));
        setContentView(R.layout.activity_main);
    }

    public void openOtpPage(View view) {
        Intent numIntent  = new Intent(MainActivity.this, NumberPage.class);
        startActivity(numIntent);
    }
}