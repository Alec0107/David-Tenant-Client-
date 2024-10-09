package com.example.dt.AUTH.Authentication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dt.AUTH.Models.Account;
import com.example.dt.AUTH.Models.Executor;
import com.example.dt.AUTH.Models.AuthResponse;
import com.example.dt.AUTH.Requests.SignupRequest;
import com.example.dt.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Signup extends AppCompatActivity {

    TextView username, email, password, confirmPassword;
    Button registerButton;
    TextView loginTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);

        registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

    }




    public void register(){

        ExecutorService executorService =  Executor.getExecutorService();
        username        = findViewById(R.id.Username);
        email           = findViewById(R.id.Email);
        password        = findViewById(R.id.Password);
        confirmPassword = findViewById(R.id.ConfirmPassword);

        boolean isEqual = true;
        boolean isPasswordEnough = false;

        String password_ = password.getText().toString();

        String email_ = email.getText().toString();
        int email_length = email_.length();

        String emailDomain = "@gmail.com";
        int domainSize = emailDomain.length();


        try {

            if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
                Toast.makeText(Signup.this, "Password do not match!", Toast.LENGTH_SHORT).show();
                return; // Exit the method early if passwords don't match
            }

            if (username.getText() == null || username.getText().toString().isEmpty() ||
                    email.getText() == null || email.getText().toString().isEmpty() ||
                    password.getText() == null || password.getText().toString().isEmpty() ||
                    confirmPassword.getText() == null || confirmPassword.getText().toString().isEmpty()) {

                Toast.makeText(Signup.this, "Fields must not be blank!", Toast.LENGTH_SHORT).show();
                throw new NullPointerException();

            }else{

                for(int i = email_length - 1, j = domainSize - 1; j >= 0; i--, j--){
                    if(email_.charAt(i) != emailDomain.charAt(j)){
                        isEqual = false;
                        Toast.makeText(Signup.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

                if(password_.length() >= 8){
                    isPasswordEnough = true;
                }else{
                    Toast.makeText(Signup.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
                }

                if(isEqual & isPasswordEnough) {

                    Account account = new Account(username.getText().toString(), email.getText().toString(), password.getText().toString());
                    Future<AuthResponse> future = executorService.submit(new SignupRequest(account));


                    class UI implements Runnable {

                        @Override
                        public void run() {
                            try {
                                final AuthResponse signupResponse =  future.get();

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        if(signupResponse.isExist()){
                                            Toast.makeText(Signup.this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(Signup.this, signupResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });// RUN ON UI THREAD



                            } catch (ExecutionException e) {
                                throw new RuntimeException(e);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }


                        }// RUN METHOD

                    }executorService.submit(new UI());

                }// if(isEqual & isPasswordEnough) {

            }// ELSE

            }catch (Exception e) {
            e.printStackTrace();
        }
    }// register();











}// class
