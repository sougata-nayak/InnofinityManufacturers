package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayContactsActivity extends AppCompatActivity {

    private TextView phoneno1;
    private TextView phoneno2;
    private TextView phoneno3;
    private TextView phoneno4;
    private TextView phoneno5;

    private Button goToEditContacts;

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contacts);

        phoneno1 = findViewById(R.id.phone1);
        phoneno2 = findViewById(R.id.phone2);
        phoneno3 = findViewById(R.id.phone3);
        phoneno4 = findViewById(R.id.phone4);
        phoneno5 = findViewById(R.id.phone5);

        goToEditContacts = (Button)findViewById(R.id.editNumbers);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String UVID = mFirebaseUser.getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(UVID).child("5_phone_numbers");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String phone1 = dataSnapshot.child("phone1").getValue().toString();
                String phone2 = dataSnapshot.child("phone2").getValue().toString();
                String phone3 = dataSnapshot.child("phone3").getValue().toString();
                String phone4 = dataSnapshot.child("phone4").getValue().toString();
                String phone5 = dataSnapshot.child("phone5").getValue().toString();

                phoneno1.setText(phone1);
                phoneno2.setText(phone2);
                phoneno3.setText(phone3);
                phoneno4.setText(phone4);
                phoneno5.setText(phone5);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        goToEditContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(DisplayContactsActivity.this, Add5ContactsActivity.class));
            }
        });
    }
}
