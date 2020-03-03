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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddNewVehicleActivity extends AppCompatActivity {

    private Spinner fuelSpinner;

    private EditText registrationNumber;
    private EditText Models;
    private Button addVehicle;
    //  UVID uvid;

    //declaring firestore object
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private FirebaseUser mFirebaseUser;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_vehicle);


        fuelSpinner=(Spinner)findViewById(R.id.fuel_spinner);
        registrationNumber=(EditText)findViewById(R.id.registration_number);
        Models=(EditText)findViewById(R.id.model_number);
        addVehicle=(Button)findViewById(R.id.add_vehicle);

        //getting the instance of firebase database
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference  = mFirebaseDatabase.getReference().child("users");

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        progressDialog = new ProgressDialog(this);


        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.setMessage("Registering Car");
                progressDialog.show();


                String UVID = mFirebaseUser.getUid();


                NewCarDetails newVehicle = new NewCarDetails(registrationNumber.getText().toString(),
                        Models.getText().toString(), fuelSpinner.getSelectedItem().toString());


                mDatabaseReference.child(UVID).child("vehicle_info").setValue(newVehicle).
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(AddNewVehicleActivity.this,
                                "Car Registered Successfully ", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddNewVehicleActivity.this, Add5ContactsActivity.class));
                        progressDialog.dismiss();
                    }
                });

            }
        });

    }
}
