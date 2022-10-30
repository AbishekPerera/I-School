package com.example.i_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RocketTimeTableUpdate extends AppCompatActivity {

     Integer Time;
     String crntDate,docId,Title,Description,sTime;
     EditText rocketViewTime,rocketViewTitle,rocketViewDescription;
     Button update_rocketBtn,delete_rocketBtn;
     DrawerLayout drawerLayout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_time_table_update);

        drawerLayout=findViewById(R.id.drawer_layout_rocket_Update);

        Intent intent = getIntent();
        crntDate = intent.getStringExtra("crntDate");
        docId = intent.getStringExtra("docId");
        sTime =  intent.getStringExtra("rocketViewTime");
        Title = intent.getStringExtra("rocketViewTitle");
        Description = intent.getStringExtra("rocketViewDescription");

        rocketViewTime = findViewById(R.id.update_rocketViewTime);
        rocketViewTitle = findViewById(R.id.update_rocketViewTitle);
        rocketViewDescription = findViewById(R.id.update_rocketViewDescription);
        update_rocketBtn =findViewById(R.id.update_rocketBtn);
        delete_rocketBtn =findViewById(R.id.delete_rocketBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        //set values to Edit text
        System.out.println(docId+" "+sTime+" "+Title+" "+Description);
        rocketViewTime.setText(sTime);
        rocketViewTitle.setText(Title);
        rocketViewDescription.setText(Description);

        //update Rockets
        update_rocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = crntDate;
                String timeS = rocketViewTime.getText().toString().trim();
                String title = rocketViewTitle.getText().toString().trim();
                String description = rocketViewDescription.getText().toString().trim();

                if(timeS.isEmpty()||title.isEmpty()||description.isEmpty()){

                    Toast.makeText(getApplicationContext(),"All fields are Require",Toast.LENGTH_SHORT).show();
                }
                else {
                    Integer time = Integer.parseInt(timeS);
                    if(time<0 || time > 60){
                        Toast.makeText(getApplicationContext(),"Time must between 0-60",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DocumentReference documentReference=firebaseFirestore.collection("RocketTimes").document(firebaseUser.getUid()).collection("RocketTasks").document(docId);
                        Map<String ,Object> RocketTime= new HashMap<>();
                        RocketTime.put("time",time);
                        RocketTime.put("date",date);
                        RocketTime.put("title",title);
                        RocketTime.put("description",description);

                        documentReference.set(RocketTime).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Rocket Updated Succesffuly",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RocketTimeTableView.this,RocketTimetableList.class));
                                Intent intentBackToList = new Intent(RocketTimeTableUpdate.this, RocketTimetableList.class);
                                intentBackToList.putExtra("crntDate", crntDate);
                                startActivity(intentBackToList);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed To Update Rocket",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RocketTimeTableUpdate.this,RocketTimetableList.class));
                            }
                        });

                    }
                }

            }
        });


        //Rocket deletion
        delete_rocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firebaseFirestore.collection("RocketTimes").document(firebaseUser.getUid()).collection("RocketTasks").document(docId);
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(RocketTimeTableUpdate.this, "Rocket Deleted", Toast.LENGTH_SHORT).show();
                        Intent intentBackToList = new Intent(RocketTimeTableUpdate.this, RocketTimetableList.class);
                        intentBackToList.putExtra("crntDate", crntDate);
                        startActivity(intentBackToList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(RocketTimeTableUpdate.this, "Rocket Deletion failed", Toast.LENGTH_SHORT).show();
                        Intent intentBackToList = new Intent(RocketTimeTableUpdate.this, RocketTimetableList.class);
                        intentBackToList.putExtra("crntDate", crntDate);
                        startActivity(intentBackToList);
                    }
                });
            }
        });

    }

    //For app drawer

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
        Intent intentBackToList = new Intent(RocketTimeTableUpdate.this, RocketTimetableList.class);
        intentBackToList.putExtra("crntDate", crntDate);
        startActivity(intentBackToList);
//        Toast.makeText(getApplicationContext(), "back btn", Toast.LENGTH_SHORT).show();
    }
}