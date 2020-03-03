package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName;
    private EditText registerPhone;
    private EditText registerEmail;
    private EditText registerPassword;
    private Button registerButton;
    private TextView goToLogin;

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        findViewByIds();


        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");


        progressDialog = new ProgressDialog(this);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Registering User");
                progressDialog.show();

                if(validateDetails()){

                    mFirebaseAuth.createUserWithEmailAndPassword(registerEmail.getText().toString(), registerPassword.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    mFirebaseUser = mFirebaseAuth.getCurrentUser();
                                    String UVID = mFirebaseUser.getUid();
                                        NewUser user = new NewUser(registerName.getText().toString(), registerPhone.getText().toString(), registerEmail.getText().toString(), UVID);

                                        mDatabaseReference.child(UVID).child("user_info").setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                Toast.makeText(RegisterActivity.this, "Registered Successfully ", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, AddNewVehicleActivity.class));
                                                progressDialog.dismiss();
                                            }
                                        });
                                }
                            });

                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }


    private void findViewByIds() {
        registerName = findViewById(R.id.nameEditText);
        registerPhone = findViewById(R.id.phoneEditText);
        registerEmail = findViewById(R.id.emailEditText);
        registerPassword = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.gotoLoginTextView);
    }


    private Boolean validateDetails(){

        Boolean result = false;

        String name = registerName.getText().toString();
        String phone = registerPhone.getText().toString();
        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();


        if(name.isEmpty()){
            Toast.makeText(this, "Please enter the Name", Toast.LENGTH_SHORT).show();
        }
        else if(phone.isEmpty()){
            Toast.makeText(this, "Please enter the Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if(email.isEmpty()){
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
