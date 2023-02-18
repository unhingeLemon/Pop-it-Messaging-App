package com.example.mobile_programming_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobile_programming_project.Adapter.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserList extends AppCompatActivity {
    private ArrayList<User> usersList;
    private RecyclerView recyclerView;
    private UserAdapter.RecyclerViewClickListener listner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        recyclerView = findViewById(R.id.rvUser);

        usersList = new ArrayList<>();
        setUserInfo();

        //System.out.println(usersList.get(0));


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

    private void setUserInfo() {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance("https://pop-it-messaging-app-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        ValueEventListener valueEventListener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //usersList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    User user = snapshot.getValue(User.class);
                    System.out.println(firebaseUser.getEmail());
                    assert user != null;
                    assert firebaseUser != null;
                    if (!user.getEmail().equals(firebaseUser.getEmail())) {
                        usersList.add(user);
                    }

                    //usersList.add(new User(user.getUsername(),user.getEmail()));

                    setAdapter();


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void backButton(View view){
        switch(view.getId()){
            case R.id.user_btnBack:
                startActivity(new Intent(UserList.this, ChatHomeActivity.class));
                break;
        }
    }
}