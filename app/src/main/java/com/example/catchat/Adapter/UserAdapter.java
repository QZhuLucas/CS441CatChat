package com.example.catchat.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.catchat.Message;
import com.example.catchat.R;
import com.example.catchat.User;


import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {



    private Context context;
    private List<User> user;
    private boolean isChat;

    public UserAdapter(Context context, List<User> user, boolean isChat){
        this.context = context;
        this.user = user;
        this.isChat = isChat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_students, viewGroup, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User users = user.get(i);
        viewHolder.username.setText("Name: " + users.getName());
        viewHolder.useremail.setText("Email: " + users.getEmailAddress());

        if(isChat){
            if(users.getStatus().equals("online")){
                viewHolder.img_on.setVisibility(View.VISIBLE);
                viewHolder.img_off.setVisibility(View.GONE);
            }else {
                viewHolder.img_off.setVisibility(View.VISIBLE);
                viewHolder.img_on.setVisibility(View.GONE);
            }
        }else{
            viewHolder.img_off.setVisibility(View.GONE);
            viewHolder.img_on.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Message.class);
                intent.putExtra("userid", users.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username, useremail;
        public ImageView profile_image,img_on,img_off;

        public ViewHolder(View itemView){
            super(itemView);

            username = itemView.findViewById(R.id.username);
            useremail = itemView.findViewById(R.id.useremail);
            profile_image = itemView.findViewById(R.id.profile_Image);
            img_on = itemView.findViewById(R.id.img_on);
            img_off = itemView.findViewById(R.id.img_off);
        }
    }


}
