package com.example.i_school;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DictUpdateDelete extends AppCompatActivity {

    String docId,Title,Description;
    EditText dictViewTitle,dictViewDescription;
    Button update_dictBtn,delete_dictBtn;
    DrawerLayout drawerLayout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_update_delete);

        drawerLayout=findViewById(R.id.drawer_layout_dict_Update);

        Intent intent = getIntent();
        docId = intent.getStringExtra("docId");
        Title = intent.getStringExtra("noteViewTitle");
        Description = intent.getStringExtra("noteViewDescription");

        dictViewTitle = findViewById(R.id.update_dictViewTitle);
        dictViewDescription = findViewById(R.id.update_dictViewDescription);
        update_dictBtn =findViewById(R.id.update_dictBtn);
        delete_dictBtn =findViewById(R.id.delete_dictBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        //set values to Edit text
        dictViewTitle.setText(Title);
        dictViewDescription.setText(Description);

        //update Word
        update_dictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = dictViewTitle.getText().toString();
                String description = dictViewDescription.getText().toString();

                if(title.isEmpty()||description.isEmpty()){

                    Toast.makeText(getApplicationContext(),"All fields are Require",Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference=firebaseFirestore.collection("MyDictionary")
                            .document(firebaseUser.getUid()).collection("Words")
                            .document(docId);
                    Map<String ,Object> Word= new HashMap<>();
                    Word.put("title",title);
                    Word.put("description",description);

                    documentReference.set(Word).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Word Updated Succesffuly",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(RocketTimeTableView.this,RocketTimetableList.class));
                            Intent intentBackToList = new Intent(DictUpdateDelete.this, DictAll.class);
                            startActivity(intentBackToList);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed To Update Word",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DictUpdateDelete.this,DictAll.class));
                        }
                    });


                }

            }
        });
        //Word deletion
        delete_dictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firebaseFirestore.collection("MyDictionary")
                        .document(firebaseUser.getUid())
                        .collection("Words")
                        .document(docId);
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DictUpdateDelete.this, "Word Deleted", Toast.LENGTH_SHORT).show();
                        Intent intentBackToList = new Intent(DictUpdateDelete.this, DictAll.class);
                        startActivity(intentBackToList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DictUpdateDelete.this, "Word Deletion failed", Toast.LENGTH_SHORT).show();
                        Intent intentBackToList = new Intent(DictUpdateDelete.this, DictAll.class);
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
        Navigation_Drawer.redirectActivity(this,NotesAllNotes.class);
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
        Intent intentBackToList = new Intent(DictUpdateDelete.this, DictAll.class);
        startActivity(intentBackToList);
//        Toast.makeText(getApplicationContext(), "back btn", Toast.LENGTH_SHORT).show();
    }
}