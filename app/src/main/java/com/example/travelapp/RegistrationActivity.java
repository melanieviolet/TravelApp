package com.example.travelapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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


public class RegistrationActivity extends AppCompatActivity {
    EditText editTextEmail, editPassword, editConfirmPassword;
    Button buttonRegister;
    ProgressBar progressBar;
    TextView loginButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        editTextEmail = findViewById(R.id.email_edit_text);
        editPassword = findViewById(R.id.textPassword);
        editConfirmPassword = findViewById(R.id.confirm_password_edit_text);
        buttonRegister = findViewById(R.id.regiButton);
        progressBar = findViewById(R.id.progressBar);
        loginButtonText = findViewById(R.id.loginTextView);

        buttonRegister.setOnClickListener(v -> registerAccount());
        loginButtonText.setOnClickListener(v -> finish());

    }
        void registerAccount(){
            String email  = editTextEmail.getText().toString();
            String password  = editPassword.getText().toString();
            String confirmPassword  = editConfirmPassword.getText().toString();

            boolean isValidated = validateData(email,password,confirmPassword);
            if(!isValidated){
                return;
            }
            createFirebaseAccount(email,password);
        }

void createFirebaseAccount(String email, String password){
loadingBar(true);
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistrationActivity.this,
            new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    loadingBar(false);
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "Registration successful!",Toast.LENGTH_SHORT).show();
                        finish();
                    }else{

                        Toast.makeText(RegistrationActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );

}

void loadingBar(boolean isInProgress){
        if(isInProgress){
            progressBar.setVisibility(View.VISIBLE);
            buttonRegister.setVisibility(View.GONE);
        } else{
            progressBar.setVisibility(View.GONE);
            buttonRegister.setVisibility(View.VISIBLE);
        }
}

    boolean validateData(String email, String password, String confirmPassword) {
        //validate user input
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Email is invalid");
            return false;
        }
        if (password.length() < 6) {
            editPassword.setError("Password is too short");
            return false;
        }
        if (!confirmPassword.equals(password)) {
            editConfirmPassword.setError("Password not matched");
            return false;
        }
        return true;

    }





}