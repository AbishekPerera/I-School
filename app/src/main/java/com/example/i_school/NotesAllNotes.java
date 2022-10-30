package com.example.i_school;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NotesAllNotes extends AppCompatActivity {

    FloatingActionButton noteAddTaskBtn;
    RecyclerView noteRecyclerView;

    DrawerLayout drawerLayout;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<firebaseNotetmodel, firebaseNotesViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_all_notes);

        noteAddTaskBtn = findViewById(R.id.noteAddTaskBtn);
        noteRecyclerView = findViewById(R.id.noterecyclerView);

        drawerLayout=findViewById(R.id.drawer_layout_notes_all);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        //Query
        Query query=firebaseFirestore.collection("MyNotes")
                .document(firebaseUser.getUid())
                .collection("Notes")
                .orderBy("title",Query.Direction.DESCENDING);

        //RecyclerOptions
        FirestoreRecyclerOptions<firebaseNotetmodel> allusernotes= new FirestoreRecyclerOptions.Builder<firebaseNotetmodel>().setQuery(query,firebaseNotetmodel.class).build();


        adapter = new FirestoreRecyclerAdapter<firebaseNotetmodel, firebaseNotesViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull firebaseNotesViewHolder holder, int position, @NonNull firebaseNotetmodel model) {

                holder.noteViewTitle.setText(model.getTitle());
                holder.noteViewDes.setText(model.getDescription());

                String docId = adapter.getSnapshots().getSnapshot(position).getId();
                System.out.println(docId);

                //add Event Listener to a card
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        Toast.makeText(getApplicationContext(), "Button Clicked" + docId, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(),NoteUpdateDelete.class);
                        intent.putExtra("docId", docId);
                        intent.putExtra("noteViewTitle", model.getTitle());
                        intent.putExtra("noteViewDescription", model.getDescription());
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public firebaseNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singe_note_card, parent,false);
                return new firebaseNotesViewHolder(view);
            }
        };

        noteRecyclerView.setHasFixedSize(true);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setAdapter(adapter);


        noteAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesAllNotes.this,NotesAdd.class));
            }
        });

    }

    private class firebaseNotesViewHolder extends RecyclerView.ViewHolder{

        private TextView noteViewTitle,noteViewDes;

        public firebaseNotesViewHolder(@NonNull View itemView) {
            super(itemView);

            noteViewTitle = itemView.findViewById(R.id.noteViewTitle);
            noteViewDes = itemView.findViewById(R.id.noteViewDes);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(adapter!=null){
            adapter.stopListening();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        adapter.startListening();

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
        Intent intentBackToList = new Intent(NotesAllNotes.this, landingPageNew.class);
        startActivity(intentBackToList);
//        Toast.makeText(getApplicationContext(), "back btn", Toast.LENGTH_SHORT).show();
    }
}