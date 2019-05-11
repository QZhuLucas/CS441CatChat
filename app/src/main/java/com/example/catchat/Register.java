package com.example.catchat;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class Register extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextEmail, editTextPassword, editTextCountry,editTextPhone, editTextConfirm;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private RadioButton genderMale, genderFemale, typeTeacher,typeStudent;
    private TextView textViewgender, textViewusertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViewById(R.id.registerButton).setOnClickListener(this);
        editTextName = findViewById(R.id.signupName);
        editTextEmail = findViewById(R.id.signupEmail);
        editTextPassword = findViewById(R.id.signupPassword);
        editTextCountry = findViewById(R.id.signupCountry);
        editTextPhone = findViewById(R.id.signupPhone);
        genderMale = findViewById(R.id.male);
        genderFemale = findViewById(R.id.female);
        typeTeacher = findViewById(R.id.teacher);
        typeStudent = findViewById(R.id.student);
        editTextPhone = findViewById(R.id.signupPhone);
        editTextConfirm = findViewById(R.id.confirmPassword);
        textViewgender = findViewById(R.id.gender);
        textViewusertype = findViewById(R.id.userType);

        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);



        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
        }
    }

    private void registerUser() {
        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirm = editTextConfirm.getText().toString().trim();
        final String country = editTextCountry.getText().toString().trim();
        final String phone = editTextPhone.getText().toString().trim();
        final String gender;
        final String typeUser;



        if (name.isEmpty()) {
            editTextName.setError("Error!Please enter your name!!@G");
            editTextName.requestFocus();
            return;
        }

        if(phone.isEmpty()){
            editTextPhone.setError("Error!Please enter your 10 digits phone number.");
            editTextPhone.requestFocus();
        }

        if(phone.trim().length()< 10){
            editTextPhone.setError("Error!Your phone number must has 10 digits.");
            editTextPhone.requestFocus();
        }

        if (country.isEmpty()) {
            editTextCountry.setError("Error!Please enter your country.");
            editTextCountry.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Error!Please enter you email.");
            editTextEmail.requestFocus();
            return;
        }



        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Error!Wrong email format.");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Error!Please enter your password.");
            editTextPassword.requestFocus();
            return;
        }

        if(!password.equals(confirm)){
            editTextConfirm.setError("Error!The password and confirm password must be the same.");
            editTextConfirm.requestFocus();
            return;
        }



        if(genderMale.isChecked()){
            gender = genderMale.getText().toString();
        }else if(genderFemale.isChecked()){
            gender = genderFemale.getText().toString();
        }else{
            textViewgender.setError("");
            textViewgender.requestFocus();
            return;
        }

        if(typeTeacher.isChecked()){
            typeUser = typeTeacher.getText().toString();
        }else if(typeStudent.isChecked()){
            typeUser = typeStudent.getText().toString();
        }else{
            textViewusertype.setError("");
            textViewusertype.requestFocus();
            return;
        }



        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            final String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(null,email,typeUser,gender,country,name,phone,id,null);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(id)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(Register.this, "Register Success", Toast.LENGTH_LONG).show();
                                        finish();
                                        startActivity(new Intent(Register.this,MainActivity.class));
                                    } else {
                                        //display a failure message
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });

                        } else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerButton:
                registerUser();
                break;
        }
    }


}
