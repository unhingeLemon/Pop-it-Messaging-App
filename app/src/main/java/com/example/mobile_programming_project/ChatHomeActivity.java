package com.example.mobile_programming_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatHomeActivity extends AppCompatActivity {

    private Button logout;
    private Button usersTab;
    private Button btnChatList;

    private FirebaseUser user;
    private DatabaseReference reference;

    private String userId;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_home);

        logout = (Button) findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ChatHomeActivity.this, LoginUser.class));
            }
        });


        usersTab = (Button) findViewById(R.id.btnUsers);
        usersTab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatHomeActivity.this, UserList.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://pop-it-messaging-app-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        userId = user.getUid();

        final TextView tvName = (TextView) findViewById(R.id.tvName);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null){
                    String name = userProfile.username;

                    tvName.setText(name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ChatHomeActivity.this, "Something went wrong", Toast.LENGTH_LONG);

            }
        });


        //direct to chatList activity

        btnChatList = (Button) findViewById(R.id.btnChatList);
        btnChatList.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChatHomeActivity.this, ChatList.class));
            }
        });




    }
}
