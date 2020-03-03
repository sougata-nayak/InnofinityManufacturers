package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add5ContactsActivity extends AppCompatActivity {

    private EditText p1;
    private EditText p2;
    private EditText p3;
    private EditText p4;
    private EditText p5;
    private Button add_phone_number;



    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private FirebaseUser mFirebaseUser;

    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add5_contacts);


        p1 = (EditText)findViewById(R.id.phone1);
        p2 = (EditText)findViewById(R.id.phone2);
        p3 = (EditText)findViewById(R.id.phone3);
        p4 = (EditText)findViewById(R.id.phone4);
        p5 = (EditText)findViewById(R.id.phone5);

        add_phone_number = (Button)findViewById(R.id.add_emergency_contacts_button);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog = new ProgressDialog(this);


        add_phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Registering your Emergency contacts");
                progressDialog.show();

                String UVID = mFirebaseUser.getUid();

                New5Contacts newContacts = new New5Contacts(p1.getText().toString(),p2.getText().toString(),p3.getText().toString(),p4.getText().toString(),p5.getText().toString());


                mDatabaseReference.child(UVID).child("5_phone_numbers").setValue(newContacts).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        Toast.makeText(Add5ContactsActivity.this, "Emergency contacts added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Add5ContactsActivity.this, MapsActivity.class));
                        progressDialog.dismiss();

                        String UVID = mFirebaseUser.getUid();

                        NewAccidentDetails accidentDetails = new NewAccidentDetails("0", "0", "0", "0", "0", "0", "0");
                        mDatabaseReference.child(UVID).child("sensor_data").setValue(accidentDetails);

                        NewLocation location = new NewLocation("0.0", "0.0");
                        mDatabaseReference.child(UVID).child("location").setValue(location);

                        mDatabaseReference.child(UVID).child("parking_mode").setValue(location);

                        mDatabaseReference.child(UVID).child("parking_mode").child("mode").setValue("0");
                    }
                });
            }
        });
    }
}
