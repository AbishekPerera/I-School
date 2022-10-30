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

public class NoteUpdateDelete extends AppCompatActivity {

    String docId,Title,Description;
    EditText noteViewTitle,noteViewDescription;
    Button update_noteBtn,delete_noteBtn;
    DrawerLayout drawerLayout;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_update_delete);

        drawerLayout=findViewById(R.id.drawer_layout_note_Update);

        Intent intent = getIntent();
        docId = intent.getStringExtra("docId");
        Title = intent.getStringExtra("noteViewTitle");
        Description = intent.getStringExtra("noteViewDescription");

        noteViewTitle = findViewById(R.id.update_noteViewTitle);
        noteViewDescription = findViewById(R.id.update_noteViewDescription);
        update_noteBtn =findViewById(R.id.update_noteBtn);
        delete_noteBtn =findViewById(R.id.delete_noteBtn);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        //set values to Edit text
        noteViewTitle.setText(Title);
        noteViewDescription.setText(Description);

        //update Rockets
        update_noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = noteViewTitle.getText().toString();
                String description = noteViewDescription.getText().toString();

                if(title.isEmpty()||description.isEmpty()){

                    Toast.makeText(getApplicationContext(),"All fields are Require",Toast.LENGTH_SHORT).show();
                }
                else {
                    DocumentReference documentReference=firebaseFirestore.collection("MyNotes")
                            .document(firebaseUser.getUid()).collection("Notes")
                            .document(docId);
                    Map<String ,Object> Note= new HashMap<>();
                    Note.put("title",title);
                    Note.put("description",description);

                        documentReference.set(Note).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"Note Updated Succesffuly",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(RocketTimeTableView.this,RocketTimetableList.class));
                                Intent intentBackToList = new Intent(NoteUpdateDelete.this, NotesAllNotes.class);
                                startActivity(intentBackToList);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Failed To Update Note",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(NoteUpdateDelete.this,NotesAllNotes.class));
                            }
                        });


                }

            }
        });


        //note deletion
        delete_noteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference documentReference = firebaseFirestore.collection("MyNotes")
                        .document(firebaseUser.getUid())
                        .collection("Notes")
                        .document(docId);
                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(NoteUpdateDelete.this, "Note Deleted", Toast.LENGTH_SHORT).show();
                        Intent intentBackToList = new Intent(NoteUpdateDelete.this, NotesAllNotes.class);
                        startActivity(intentBackToList);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NoteUpdateDelete.this, "Note Deletion failed", Toast.LENGTH_SHORT).show();
                        Intent intentBackToList = new Intent(NoteUpdateDelete.this, NotesAllNotes.class);
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
        Navigation_Drawer.redirectActivity(this, RocketTimeTable.class);
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
        Intent intentBackToList = new Intent(NoteUpdateDelete.this, NotesAllNotes.class);
        startActivity(intentBackToList);
//        Toast.makeText(getApplicationContext(), "back btn", Toast.LENGTH_SHORT).show();
    }
}