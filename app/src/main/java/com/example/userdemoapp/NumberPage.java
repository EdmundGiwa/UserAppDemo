package com.example.userdemoapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NumberPage extends AppCompatActivity {

    EditText mCountryCode, phoneNumField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(Color.rgb(255, 255, 255));

        setContentView(R.layout.activity_number_page);

        mCountryCode = findViewById(R.id.countryCode);
        phoneNumField = findViewById(R.id.num_textField);
    }

    public void verifyNumber(View view) {
        String countryCode = mCountryCode.getText().toString();
        String phoneNumber = phoneNumField.getText().toString();

        String complete_phonenumber = "+"+countryCode  + phoneNumber;

        if(countryCode.isEmpty() || phoneNumber.isEmpty()){
            mCountryCode.setError("Please input country code");
            phoneNumField.setError("Please input phone number");
        }


        else{
            Intent intent = new Intent(getApplicationContext(), OtpPage.class);
            intent.putExtra("phoneNo",complete_phonenumber);
            startActivity(intent);
        }
    }
//    public void openVerPage(View view) {
//        Intent numIntent  = new Intent(NumberPage.this, OtpPage.class);
//        startActivity(numIntent);
//    }
}