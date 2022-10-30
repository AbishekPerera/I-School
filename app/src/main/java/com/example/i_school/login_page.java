package com.example.i_school;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login_page extends AppCompatActivity {
    //decare var for catch ui ids
    private EditText loginEmail,loginPassword;
    private Button loginAccbtn;
    //declare var for firebase auth and get connection
    private FirebaseAuth firebaseAuth;
    private ProgressBar LoginProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        //catch ui ids
        LoginProgressBar=findViewById(R.id.LoginProgressBar);
        //catch ui ids
        loginEmail = findViewById(R.id.Login_page_Email_textinput);
        loginPassword = findViewById(R.id.Login_page_pw_text);
        loginAccbtn = findViewById(R.id.Login_page_Login_btn);
        //get user info
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
        if(firebaseUser!=null)
        {
            finish();
            startActivity(new Intent(login_page.this,landingPageNew.class));
        }
        loginAccbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                //disable progras bar
                LoginProgressBar.setVisibility(View.VISIBLE);

                if(email.isEmpty() || password.isEmpty()){
                    //check password and email are filled
                    LoginProgressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"All Field Are Required",Toast.LENGTH_SHORT).show();
                }
                else {
                    //login to the app
//                    startActivity(new Intent(login_page.this,landingPageNew.class));
                    firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                checkmailverfication();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                                LoginProgressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });

                }
            }
        });
    }

    private void checkmailverfication(){ // email validation function starts
        FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();

        if(firebaseUser.isEmailVerified()==true)
        {
            Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(login_page.this,landingPageNew.class));
        }
        else
        {
            LoginProgressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(),"Verify your mail first",Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }
    }//function eds
}