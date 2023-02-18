package com.example.mobile_programming_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginUser extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPassword;
    private EditText login_email,login_password;
    private Button login_btnLogin;

    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        register = (TextView) findViewById(R.id.login_btnRegister);
        register.setOnClickListener(this);

        login_btnLogin = (Button) findViewById(R.id.login_btnLogin);
        login_btnLogin.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);
        progressBar.setVisibility(View.GONE);

        login_email = (EditText) findViewById(R.id.login_email);
        login_password = (EditText) findViewById(R.id.login_password);

        forgotPassword = (TextView) findViewById(R.id.login_btnForgetPass);
        forgotPassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_btnRegister:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login_btnLogin:
                userLogin();
                break;
            case R.id.login_btnForgetPass:
                startActivity(new Intent(this,ForgotPassword.class));
                break;
        }
    }


    private void userLogin(){
        String email = login_email.getText().toString().trim();
        String password = login_password.getText().toString().trim();

        if(email.isEmpty()){
            login_email.setError("Email Required");
            login_email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            login_email.setError("Please Provide valid email");
            login_email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            login_password.setError("Password Required");
            login_password.requestFocus();
            return;
        }

        if(password.length() < 6){
            login_password.setError("Minimum password length must be 6 characters!");
            login_password.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    //direct to user main screen
                    startActivity(new Intent(LoginUser.this,ChatHomeActivity.class));
                }else{
                    Toast.makeText(LoginUser.this,"Login Failed", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }


            }
        });


    }
}
