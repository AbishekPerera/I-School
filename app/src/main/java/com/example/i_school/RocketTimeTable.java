package com.example.i_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class RocketTimeTable extends AppCompatActivity {

    DrawerLayout drawerLayout;
    FirebaseAuth firebaseAuth;

    Calendar calendar;
    CalendarView calendarView;
    String crntDate;

    TextView roketdateinput;
    Button rocketSelectDateBtn;
    Boolean isChangedDate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_time_table);

        drawerLayout=findViewById(R.id.drawer_layout);
        firebaseAuth = FirebaseAuth.getInstance();

        calendarView = findViewById(R.id.rocketcalendarView);

        roketdateinput = findViewById(R.id.rocketSetdateTextVv);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                crntDate = i+ "/" +i1+ "/" +i2+ "";
                roketdateinput.setText(crntDate);
                isChangedDate=true;
            }
        });

        rocketSelectDateBtn = findViewById(R.id.rocketSelectDateBtn);

        rocketSelectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(isChangedDate){
                    //goto next page
                    Intent intent = new Intent(RocketTimeTable.this, RocketTimetableList.class);
                    intent.putExtra("crntDate",crntDate);
                    startActivity(intent);
                }
                else {
                   Toast.makeText(getApplicationContext(),"Please select a date",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void ClickMenu(View view){
        Navigation_Drawer.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        Navigation_Drawer.closeDrawer(drawerLayout);
    }

    public void Clickhome(View view){
        Navigation_Drawer.redirectActivity(this, landingPageNew.class);
    }

    public void ClickRocket(View view){
        recreate();
    }

    public void ClickDictionary(View view){
        Navigation_Drawer.redirectActivity(this,LandingPage.class);
    }

    public void ClickCal(View view){
        Navigation_Drawer.redirectActivity(this,LandingPage.class);
    }

    public void ClickOR(View view){
        Navigation_Drawer.redirectActivity(this,loginReg.class);
    }
    public void ClickmyNotes(View view){
        Navigation_Drawer.redirectActivity(this,LandingPage.class);
    }

    public void ClickLogout(View view){
        Navigation_Drawer.redirectActivity(this,loginReg.class);
        firebaseAuth.signOut();

    }

    public void Clicktodo(View view){
        Navigation_Drawer.redirectActivity(this,LandingPage.class);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Navigation_Drawer.closeDrawer(drawerLayout);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RocketTimeTable.this,landingPageNew.class));
    }

}