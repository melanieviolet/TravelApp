package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    EditText editEmail, editPassword;
    Button buttonLogin;
    ProgressBar progressBar;
    TextView registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editEmail = findViewById(R.id.email_edit_text);
        editPassword = findViewById(R.id.textPassword);
        buttonLogin = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);
        registerButton = findViewById(R.id.register_textviewbutton);

        buttonLogin.setOnClickListener((v) -> loginUser());
        registerButton.setOnClickListener((v) -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));

    }

    void loginUser(){
        String email  = editEmail.getText().toString();
        String password  = editPassword.getText().toString();
        
        boolean isValidated = validateData(email,password);
        if(!isValidated){
            return;
        }
        loginAccountInFirebase(email,password);
    }

    void loginAccountInFirebase(String email,String password){
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        loadingBar(true);
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                loadingBar(false);
                if(task.isSuccessful()){
                    //login is successful
                    Toast.makeText(LoginActivity.this, "Login successful!",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                }else{
                    //login failed

                    Toast.makeText(LoginActivity.this, "Please check your details and try again",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void loadingBar(boolean isInProgress){
        if(isInProgress){
            progressBar.setVisibility(View.VISIBLE);
            buttonLogin.setVisibility(View.GONE);
        } else{
            progressBar.setVisibility(View.GONE);
            buttonLogin.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email, String password) {
        //validate user input
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6) {
            editPassword.setError("Password length is invalid");
            return false;
        }
        return true;

    }



}
