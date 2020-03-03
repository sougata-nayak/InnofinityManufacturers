package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DisplayInfoActivity extends AppCompatActivity {

    private TextView nameUser;
    private TextView phoneUser;
    private TextView emailUser;
    private TextView uvidUser;


    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_info);

        nameUser = findViewById(R.id.name);
        phoneUser = findViewById(R.id.phone);
        emailUser = findViewById(R.id.email);
        uvidUser = findViewById(R.id.uvid);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String UVID = mFirebaseUser.getUid();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(UVID).child("user_info");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String myname = dataSnapshot.child("userName").getValue().toString();
                String myphone = dataSnapshot.child("userPhone").getValue().toString();
                String myemail = dataSnapshot.child("userEmail").getValue().toString();
                String myuvid = dataSnapshot.child("uvid").getValue().toString();

                nameUser.setText("Name- " + myname);
                phoneUser.setText("Phone- " + myphone);
                emailUser.setText("Email- " + myemail);
                uvidUser.setText("UVID- " + myuvid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
