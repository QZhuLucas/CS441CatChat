package com.example.catchat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateCls extends AppCompatActivity {

    private EditText courseID,courseInfo;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String ID, INFO, currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_cls);

        courseID = findViewById(R.id.courseID);
        courseInfo = findViewById(R.id.courseInfo);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);

    }

    public void add_onClick(View view){
        AddCls();

    }

    public void AddCls(){
        ID = courseID.getText().toString();
        INFO = courseInfo.getText().toString();
        Course course = new Course(ID,INFO,null);
        databaseReference.child("course").child(ID).setValue(course);
        Toast.makeText(CreateCls.this, "Successful add class", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this,DashboardT.class));
    }

}
