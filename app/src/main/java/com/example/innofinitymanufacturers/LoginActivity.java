package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {


    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginButton;
    private TextView registerPage;

    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewByIds();

        mFirebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Logging in");
                progressDialog.show();


                if(validateDetails()) {

                    mFirebaseAuth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        finish();
                                        startActivity(new Intent(LoginActivity.this, MapsActivity.class));
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "Login unsuccessful, please check your connection", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                }

            }
        });


        registerPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }





    private void findViewByIds() {
        loginEmail = findViewById(R.id.emailLoginEditText);
        loginPassword = findViewById(R.id.passwordLoginEditText);
        loginButton = findViewById(R.id.loginButton);
        registerPage = findViewById(R.id.registerTextView);
    }


    private Boolean validateDetails(){

        Boolean result = false;


        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();

        if(email.isEmpty()){
            Toast.makeText(this, "Please enter the Email", Toast.LENGTH_SHORT).show();
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Please enter the Password", Toast.LENGTH_SHORT).show();
        }
        else{
            result = true;
        }

        return result;
    }
}
