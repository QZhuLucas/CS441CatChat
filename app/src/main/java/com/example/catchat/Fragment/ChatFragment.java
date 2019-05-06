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
import com.example.catchat.Chat;
import com.example.catchat.Chatlist;
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


public class ChatFragment extends Fragment {


    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> mUsers;
    private FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    private List<Chatlist> usersList;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        usersList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chatlist chatlist = snapshot.getValue(Chatlist.class);
                    usersList.add(chatlist);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    private void chatList() {
        mUsers = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String userName = snapshot.child("name").getValue().toString();
                    String userCountry = snapshot.child("country").getValue().toString();
                    String userEmail = snapshot.child("emailAddress").getValue().toString();
                    String userType = snapshot.child("userType").getValue().toString();
                    String userGender = snapshot.child("gender").getValue().toString();
                    String userPhone  = snapshot.child("phone").getValue().toString();
                    String userCourse;
                    if(snapshot.child("course").exists()){
                        userCourse = snapshot.child("course").getValue().toString();
                    }else{
                        userCourse = null;
                    }
                    String userID = snapshot.child("id").getValue().toString();
                    String status = snapshot.child("status").getValue().toString();
                    User user = new User(userCourse,userEmail,userType,userGender,userCountry,userName,userPhone,userID,status);
                    for (Chatlist chatlist : usersList){
                        if (user.getId().equals(chatlist.getId())){
                            mUsers.add(user);
                        }
                    }
                }
                userAdapter = new UserAdapter(getContext(), mUsers, true);
                recyclerView.setAdapter(userAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }








}
