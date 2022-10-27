package com.example.i_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class RocketTimetableList extends AppCompatActivity {

    String crntDate;
    DrawerLayout drawerLayout;

    Integer totalUsed =0;
    Integer totalWasted =0;
    TextView totalUSedText,totalWasedText;


    FloatingActionButton rocketAddTaskBtn;
    private FirebaseAuth firebaseAuth;

    RecyclerView roketRecyclerView;

    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<firebaseRocketmodel,firebaseRocketViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rocket_timetable_list);

        //create connection to add btn
        Intent intent = getIntent();
        crntDate = intent.getStringExtra("crntDate");

        Toast.makeText(getApplicationContext(),crntDate,Toast.LENGTH_SHORT).show();

        roketRecyclerView = findViewById(R.id.roketrecyclerView);
        drawerLayout=findViewById(R.id.drawer_layout_rocket_all);

        //for caclulation
        totalUSedText = findViewById(R.id.totalUSedText);
        totalWasedText = findViewById(R.id.totalWasedText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();



        rocketAddTaskBtn = findViewById(R.id.rocketAddTaskBtn);

        rocketAddTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intentToAddRocket = new Intent(RocketTimetableList.this, RocketTimeTableView.class);
               intentToAddRocket.putExtra("crntDate", crntDate);
               startActivity(intentToAddRocket);

            }
        });// end of the on click method



        //Query
        Query query=firebaseFirestore.collection("RocketTimes").document(firebaseUser.getUid()).collection("RocketTasks").whereEqualTo("date",crntDate).orderBy("time",Query.Direction.DESCENDING);

        //RecyclerOptions
        FirestoreRecyclerOptions<firebaseRocketmodel> allusernotes= new FirestoreRecyclerOptions.Builder<firebaseRocketmodel>().setQuery(query,firebaseRocketmodel.class).build();


         adapter = new FirestoreRecyclerAdapter<firebaseRocketmodel, firebaseRocketViewHolder>(allusernotes) {
            @NonNull
            @Override
            public firebaseRocketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rocket_card_item, parent,false);
                System.out.println("cccccccccccccccccccccccccccccccccccccccccccccc");
                System.out.println(view);
                return new firebaseRocketViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull firebaseRocketViewHolder holder, int position, @NonNull firebaseRocketmodel model) {
                System.out.println("modelddddddddddddddddddddddddddddddddddddddddddddd");
                System.out.println(model.getTime()+""+ model.getTitle()+""+ model.getDescription());
                System.out.println(holder);

                //Used Time calculation
                totalUsed = totalUsed + model.getTime();
                Integer h = totalUsed/60;
                Integer m = totalUsed%60;
                totalUSedText.setText("Used Time :\n"+h+ "H "+m+ " Min");

                totalWasted = 1080 - totalUsed;
                Integer hw = totalWasted/60;
                Integer mw = totalWasted%60;

                totalWasedText.setText("Wasted Time :\n"+hw+ "H "+mw+ " Min");


                holder.rocketViewTime.setText(model.getTime()+" Min");
                holder.rocketViewTitle.setText(model.getTitle());
                holder.rocketViewDescription.setText(model.getDescription());

                String docId = adapter.getSnapshots().getSnapshot(position).getId();
                System.out.println(docId);


//                holder.rocketViewTime.setText(model.getTime());
//                holder.rocketViewTitle.setText(model.getTitle());
//                holder.rocketViewDescription.setText(model.getDescription());

                //add Event Listener to a card
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Button Clicked", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(view.getContext(),RocketTimeTableUpdate.class);
                        intent.putExtra("docId", docId);
                        intent.putExtra("crntDate", crntDate);
                        intent.putExtra("rocketViewTime",  model.getTime().toString());
                        intent.putExtra("rocketViewTitle", model.getTitle());
                        intent.putExtra("rocketViewDescription", model.getDescription());
                        startActivity(intent);

                    }
                });

            }
        };


        roketRecyclerView.setHasFixedSize(true);
        roketRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        roketRecyclerView.setAdapter(adapter);

//        Query query=firebaseFirestore.collection("RocketTimes").document(firebaseUser.getUid()).collection("RocketTasks").orderBy("date",Query.Direction.ASCENDING);
//        FirestoreRecyclerOptions<firebasemodel> allusernotes= new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

    }


    public class firebaseRocketViewHolder extends RecyclerView.ViewHolder{

        //declare from layout file
        private TextView rocketViewTime;
        private TextView rocketViewTitle;
        private TextView rocketViewDescription;

        public firebaseRocketViewHolder(@NonNull View itemView) {
            super(itemView);

            rocketViewTime = itemView.findViewById(R.id.rocketViewTime);
            rocketViewTitle = itemView.findViewById(R.id.rocketViewTitle);
            rocketViewDescription = itemView.findViewById(R.id.rocketViewDescription);
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
        startActivity(new Intent(RocketTimetableList.this,RocketTimeTable.class));
    }
}