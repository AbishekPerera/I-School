package com.example.i_school;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class loginReg extends AppCompatActivity {

    private Button  mloginbtn,mregbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_reg);

        mloginbtn = findViewById(R.id.Login_Reg_Login_btn);
        mregbtn = findViewById(R.id.Login_Reg_Reg_btn);

        mloginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginReg.this,login_page.class);
                startActivity(intent);
            }
        });

        mregbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(loginReg.this,RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}