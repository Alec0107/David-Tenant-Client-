package com.example.dt.AUTH.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dt.AUTH.Models.Account;
import com.example.dt.AUTH.Models.AuthResponse;
import com.example.dt.AUTH.Models.Executor;
import com.example.dt.AUTH.Requests.LoginRequest;
import com.example.dt.AUTH.View_Activity.MainActivity;
import com.example.dt.R;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Login extends AppCompatActivity {


    TextView email, password, signuptxt;
    Button loginButton;
    View loginform, progess;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email       = findViewById(R.id.Email);
        password    = findViewById(R.id.Password);
        signuptxt   = findViewById(R.id.SignupTxt);
        loginButton = findViewById(R.id.LoginButton);
        loginform   = findViewById(R.id.loginForm);
        progess     = findViewById(R.id.progress);


        signupActivity(signuptxt);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString    = email.getText().toString().trim();
                String passwordString = password.getText().toString().trim();
                try {

                    login(emailString, passwordString);

                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });


    }

    public void showloading(boolean isloading){
        if(isloading){
            loginform.setVisibility(View.GONE);
            progess.setVisibility(View.VISIBLE);
        }else{
            progess.setVisibility(View.GONE);
            loginform.setVisibility(View.VISIBLE);
        }
    }


    public void login(String email, String password) throws ExecutionException, InterruptedException {

        showloading(true);

        ExecutorService executorService = Executor.getExecutorService();

        String emailDomain = "@gmail.com";
        boolean isEmailValid     = true;
        boolean isPasswordEnough = false;

        for(int i = email.length() - 1, j = emailDomain.length() - 1; j >= 0; i--, j--){
            if(email.charAt(i) != emailDomain.charAt(j)){
                isEmailValid = false;
                Toast.makeText(Login.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();
                showloading(false);
                break;
            }
        }

        if(password.length() > 8){
            isPasswordEnough = true;
        }else{
            Toast.makeText(Login.this, "Password must be at least 8 characters", Toast.LENGTH_SHORT).show();
        }

        if(isEmailValid && isPasswordEnough){
            Account account = new Account(email, password);
            Future<AuthResponse> future = executorService.submit(new LoginRequest(account));


            class UI implements Runnable{

                @Override
                public void run() {
                    try{
                        final AuthResponse authResponse = future.get();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(authResponse.isExist()){
                                    showloading(false);
                                    Toast.makeText(Login.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent ProductIntent = new Intent(Login.this, MainActivity.class);
                                    startActivity(ProductIntent);
                                    finish();
                                }else{
                                    Toast.makeText(Login.this, authResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent signupIntent = new Intent(Login.this, Signup.class);
                                    startActivity(signupIntent);
                                    finish();
                                }
                            }
                        });

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

            }executorService.submit(new UI());

        }
    }// login()

    public void signupActivity(TextView signupTxt){
        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupIntent = new Intent(Login.this, Signup.class);
                startActivity(signupIntent);
            }
        });
    }


}









