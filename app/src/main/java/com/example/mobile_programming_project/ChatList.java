package com.example.mobile_programming_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_programming_project.Adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatList extends AppCompatActivity {

    private ArrayList<User> currentUser;
    RecyclerView recyclerView;
    FirebaseUser fUser;
    DatabaseReference reference;

    private UserAdapter.RecyclerViewClickListener listner;

    private ArrayList<String> usersListString;
    private ArrayList<User> usersList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_list);




        recyclerView = findViewById(R.id.rvUser_List);





        usersListString = new ArrayList<>();
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance("https://pop-it-messaging-app-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersListString.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);



                    if(fUser.getUid().equals(chat.getSender())){
                        if(!usersListString.contains(chat.getReceiver())){
                            usersListString.add(chat.getReceiver());
                        }
                    }

                    if(fUser.getUid().equals(chat.getReceiver())){
                        if(!usersListString.contains(chat.getSender())){
                            usersListString.add(chat.getSender());
                        }
                    }
//
//                    if(chat.getReceiver().equals(fUser.getUid())){
//                        usersListString.add(chat.getSender());
//                    }

                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    private void readChats(){
        usersList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance("https://pop-it-messaging-app-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                   User user = snapshot.getValue(User.class);



                    for(String id : usersListString) {
                        if(user.getId().equals(id)){
                            usersList.add(user);
                        }
                    }

                    setAdapter();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void setAdapter() {
        setOnClickListener();


        UserAdapter adapter = new UserAdapter(usersList,listner);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setOnClickListener() {
        listner = new UserAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent intent = new Intent(getApplicationContext(),MessageActivity.class);
                intent.putExtra("name",usersList.get(position).getUsername());
                intent.putExtra("email",usersList.get(position).getEmail());
                intent.putExtra("userId",usersList.get(position).getId());
                startActivity(intent);
            }
        };
    }

    public void backButton(View view){
        switch(view.getId()){
            case R.id.chat_btnBack:
                startActivity(new Intent(ChatList.this, ChatHomeActivity.class));
        }
    }



}
