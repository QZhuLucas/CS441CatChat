package com.example.catchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddStudent extends AppCompatActivity {

    private Spinner spinner;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        spinner = findViewById(R.id.selectcls);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID).child("course");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList <String> courseList = new ArrayList<>();
//                while (dataSnapshot.exists()){
//                    courseList.add(dataSnapshot.getValue().toString());
//                }

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {
                    String courseName = areaSnapshot.child("coutseInfo").getValue().toString();
                    courseList.add(courseName);
                }

                ArrayAdapter <String>adapter = new ArrayAdapter<String>(AddStudent.this,android.R.layout.simple_spinner_item, courseList);
                spinner.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void select_Onclick(View view){

    }

}
