package com.example.dt.AUTH.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dt.R;

public class PreLogin extends AppCompatActivity {

    Button signup, login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pre_login);


        signup = findViewById(R.id.SignupButton);
        login  = findViewById(R.id.LoginButton);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(PreLogin.this, Signup.class);
                startActivity(signupIntent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(PreLogin.this, Login.class);
                startActivity(loginIntent);
            }
        });



    }


}