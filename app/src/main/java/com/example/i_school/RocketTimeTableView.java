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

public class RocketTimeTableView extends AppCompatActivity {


    String crntDate;
    DrawerLayout drawerLayout;

    EditText RoketAddtime,RoketAddtitle,RoketAdddescription;
    Button saveRocketNote;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_time_table_view);

        Intent intent = getIntent();
        crntDate = intent.getStringExtra("crntDate");

        drawerLayout=findViewById(R.id.drawer_layout_rocket_View);

        RoketAddtime = findViewById(R.id.RoketAddtime);
        RoketAddtitle = findViewById(R.id.RoketAddtitle);
        RoketAdddescription = findViewById(R.id.RoketAdddescription);

        saveRocketNote = findViewById(R.id.saveRocketNote);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        saveRocketNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = crntDate;
                String timeS = RoketAddtime.getText().toString().trim();
                String title = RoketAddtitle.getText().toString().trim();
                String description = RoketAdddescription.getText().toString().trim();

                if(timeS.isEmpty()||title.isEmpty()||description.isEmpty()){

                    Toast.makeText(getApplicationContext(),"All fields are Require",Toast.LENGTH_SHORT).show();
                }
                else {
                    Integer time = Integer.parseInt(timeS);
                    if(time<0 || time > 60){
                        Toast.makeText(getApplicationContext(),"Time must between 0-60",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DocumentReference documentReference=firebaseFirestore.collection("RocketTimes")
                                .document(firebaseUser.getUid()).collection("RocketTasks").document();
                        Map<String ,Object> RocketTime= new HashMap<>();
                        RocketTime.put("time",time);
                        RocketTime.put("date",date);
                        RocketTime.put("title",title);
                        RocketTime.put("description",description);

                        documentReference.set(RocketTime).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Rocket Created Succesffuly",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RocketTimeTableView.this,RocketTimetableList.class));
                                Intent intentBackToList = new Intent(RocketTimeTableView.this, RocketTimetableList.class);
                                intentBackToList.putExtra("crntDate", crntDate);
                                startActivity(intentBackToList);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed To Create Rocket",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RocketTimeTableView.this,RocketTimetableList.class));
                            }
                        });

                    }
                }

            }//end
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
        Intent intentBackToList = new Intent(RocketTimeTableView.this, RocketTimetableList.class);
        intentBackToList.putExtra("crntDate", crntDate);
        startActivity(intentBackToList);
//        Toast.makeText(getApplicationContext(), "back btn", Toast.LENGTH_SHORT).show();
    }
}