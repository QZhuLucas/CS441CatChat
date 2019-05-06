package com.example.catchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardT extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void profile_onClick(View view){
        Intent gotoProfile = new Intent(DashboardT.this,Profile.class);
        startActivity(gotoProfile);
    }


    public void signout_onClick(View view){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void create_onClick(View view){
        startActivity(new Intent (this,CreateCls.class));
    }

    public void add_onClick(View view){
        startActivity(new Intent (this,AddStudent.class));
    }



}
