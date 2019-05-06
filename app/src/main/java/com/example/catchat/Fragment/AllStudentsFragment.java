package com.example.catchat.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catchat.Adapter.UserAdapter;
import com.example.catchat.R;
import com.example.catchat.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllStudentsFragment extends Fragment {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View view = inflater.inflate(R.layout.fragment_all_students, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        readUsers();

        return view;
    }

    private void readUsers() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    if(snapshot.child("userType").getValue().toString().equals("Student")){
                        String name = snapshot.child("name").getValue().toString();
                        String country = snapshot.child("country").getValue().toString();
                        String gender = snapshot.child("gender").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String userType = snapshot.child("userType").getValue().toString();
                        String emailAddress = snapshot.child("emailAddress").getValue().toString();
                        String id  = snapshot.child("id").getValue().toString();
                        String status = snapshot.child("status").getValue().toString();
                        User user = new User(null,emailAddress,userType,gender,country,name,phone,id,status);
                        mUsers.add(user);
                    }

                }

                userAdapter = new UserAdapter(getContext(),mUsers,false);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
