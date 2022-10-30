package com.example.i_school;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

public class DictAll extends AppCompatActivity {

    FloatingActionButton dictAddTaskBtn;
    RecyclerView dictRecyclerView;

    DrawerLayout drawerLayout;

    private FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<firebaseDicttmodel, firebaseDictsViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dict_all);

        dictAddTaskBtn = findViewById(R.id.dictAddTaskBtn);
        dictRecyclerView = findViewById(R.id.dictrecyclerView);

        drawerLayout=findViewById(R.id.drawer_layout_dict_all);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        dictAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DictAll.this,DictAdd.class));
            }
        });

        //Query
        Query query=firebaseFirestore.collection("MyDictionary")
                .document(firebaseUser.getUid())
                .collection("Words")
                .orderBy("title",Query.Direction.DESCENDING);

        //RecyclerOptions
        FirestoreRecyclerOptions<firebaseDicttmodel> allusernotes= new FirestoreRecyclerOptions.Builder<firebaseDicttmodel>().setQuery(query,firebaseDicttmodel.class).build();

        adapter = new FirestoreRecyclerAdapter<firebaseDicttmodel, firebaseDictsViewHolder>(allusernotes) {
            @Override
            protected void onBindViewHolder(@NonNull firebaseDictsViewHolder holder, int position, @NonNull firebaseDicttmodel model) {

                System.out.println(" " + model.getTitle()+" " + model.getDescription());
                holder.DictViewTitle.setText(model.getTitle());
                holder.DictViewDes.setText(model.getDescription());

                String docId = adapter.getSnapshots().getSnapshot(position).getId();
                System.out.println(docId);

//                add Event Listener to a card
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Button Clicked" + docId, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(),DictUpdateDelete.class);
                        intent.putExtra("docId", docId);
                        intent.putExtra("noteViewTitle", model.getTitle());
                        intent.putExtra("noteViewDescription", model.getDescription());
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public firebaseDictsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_dict_card, parent,false);
                return new firebaseDictsViewHolder(view);
            }
        };

        dictRecyclerView.setHasFixedSize(true);
        dictRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        dictRecyclerView.setAdapter(adapter);

    }

    private class firebaseDictsViewHolder extends RecyclerView.ViewHolder{

        private TextView DictViewTitle,DictViewDes;

        public firebaseDictsViewHolder(@NonNull View itemView) {
            super(itemView);

            DictViewTitle = itemView.findViewById(R.id.dictViewTitle);
            DictViewDes = itemView.findViewById(R.id.dictViewDes);
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
        Intent intentBackToList = new Intent(DictAll.this, landingPageNew.class);
        startActivity(intentBackToList);
//        Toast.makeText(getApplicationContext(), "back btn", Toast.LENGTH_SHORT).show();
    }

}