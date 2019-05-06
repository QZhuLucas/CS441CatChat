package com.example.catchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText userEmail, userPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Button loginButton;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.userID);
        userPassword = findViewById((R.id.password));
        loginButton = findViewById(R.id.loginButton);
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        //attaching click listener
        loginButton.setOnClickListener(this);



    }

    public void register_OnClick(View view){
        Intent register = new Intent(this,Register.class);
        startActivity(register);
    }


    private void userLogin(){
        String email = userEmail.getText().toString().trim();
        String password  = userPassword.getText().toString().trim();


        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog
        progressDialog.setMessage("Logging in Please Wait...");
        progressDialog.show();


            //logging in the user
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            //if the task is successfull
                            if(task.isSuccessful()){
//                                start the dashboard activity
                                Toast.makeText(MainActivity.this,"Login Success",Toast.LENGTH_LONG).show();
                                finish();

                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference uidRef = databaseReference.child("Users").child(uid);
                                ValueEventListener valueEventListener = new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.child("userType").getValue().toString().equals("Teacher")) {

                                            startActivity(new Intent(MainActivity.this, DashboardT.class));
                                        } else{
                                            startActivity(new Intent(MainActivity.this, DashboardS.class));
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                };
                                uidRef.addListenerForSingleValueEvent(valueEventListener);

                            }else{
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,"Wrong User/Password",Toast.LENGTH_LONG).show();
                            }
                        }
                    });



    }


    @Override
    public void onClick(View view) {
        if(view == loginButton){
            userLogin();
        }
    }



}
