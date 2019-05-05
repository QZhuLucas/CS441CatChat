package com.example.catchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {

    private TextView name,email,country,gender,phone,type,course;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        country = findViewById(R.id.userCountry);
        gender = findViewById(R.id.userGender);
        phone = findViewById(R.id.userPhone);
        type = findViewById(R.id.userType);
        course = findViewById(R.id.userCourse);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String userName = dataSnapshot.child("name").getValue().toString();
                    String userCountry = dataSnapshot.child("country").getValue().toString();
                    String userEmail = dataSnapshot.child("emailAddress").getValue().toString();
                    String userType = dataSnapshot.child("userType").getValue().toString();
                    String userGender = dataSnapshot.child("gender").getValue().toString();
                    String userPhone  = dataSnapshot.child("phone").getValue().toString();
                    if(dataSnapshot.child("course").exists()){
                        String userCourse = dataSnapshot.child("course").getValue().toString();
                        course.setText(userCourse);
                    }

                    name.setText(userName);
                    email.setText(userEmail);
                    country.setText(userCountry);
                    phone.setText(userPhone);
                    type.setText(userType);
                    gender.setText(userGender);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



}
