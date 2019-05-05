package com.example.catchat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardS extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_s);

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void profile_onClick(View view){
        Intent gotoProfile = new Intent(DashboardS.this,Profile.class);
        startActivity(gotoProfile);
    }

    public void signout_onClick(View view){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }

    public void chat_Onclick(View view){
        startActivity(new Intent(this,ChatRoom.class));
    }


}
