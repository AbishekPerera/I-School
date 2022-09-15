package com.example.i_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class landingPageNew extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_new);
    }
    public void ClickRocket(View view)
    {
        Intent i = new Intent(landingPageNew.this,RocketTimeTable.class);
        startActivity(i);
    }

}