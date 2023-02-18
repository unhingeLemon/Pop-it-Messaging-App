package com.example.mobile_programming_project.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_programming_project.LoginUser;
import com.example.mobile_programming_project.MessageActivity;
import com.example.mobile_programming_project.R;
import com.example.mobile_programming_project.RegisterUser;
import com.example.mobile_programming_project.User;
import com.example.mobile_programming_project.UserList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter <UserAdapter.MyViewHolder>{

    private ArrayList<User> usersList;
    private RecyclerViewClickListener listener;


    public UserAdapter(ArrayList<User> userList, RecyclerViewClickListener listner){
        this.usersList = userList;
        this.listener = listner;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView nametxt;

        public MyViewHolder(final View view){
            super(view);

            nametxt = view.findViewById(R.id.username);

            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());

        }
    }

    @NonNull
    @Override
    public UserAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        String name = usersList.get(position).getUsername();
        holder.nametxt.setText(name);


        if(position%2==0){
            holder.itemView.setBackgroundColor(Color.parseColor("#5637b0"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#4c319d"));
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }



}
