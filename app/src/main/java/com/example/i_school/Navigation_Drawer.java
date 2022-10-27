package com.example.i_school;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Navigation_Drawer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        drawerLayout=findViewById(R.id.drawer);
        firebaseAuth=FirebaseAuth.getInstance();

    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void Clickhome(View view){
        recreate();
    }

    public void ClickmyNotes(View view){
        redirectActivity(this,LandingPage.class);
    }

    public void Clicktodo(View view){
        redirectActivity(this,LandingPage.class);
    }

    public void ClickRocket(View view){
        redirectActivity(this,RocketTimeTable.class);
    }

    public void ClickDictionary(View view){
        redirectActivity(this,LandingPage.class);
    }

    public void ClickCal(View view){
        redirectActivity(this,LandingPage.class);
    }

    public void ClickOR(View view){
        redirectActivity(this,loginReg.class);
    }

    public void ClickLogout(View view){
        redirectActivity(this,loginReg.class);
        firebaseAuth.signOut();

    }







    public static void redirectActivity(Activity activity,Class Class){

        Intent intent = new Intent(activity,Class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}