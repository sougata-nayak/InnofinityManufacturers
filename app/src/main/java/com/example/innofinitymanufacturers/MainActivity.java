package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {


    private Switch parking_switch;
    private Button alertOff;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private DatabaseReference accident_database_reference;


    private TextView alert,fire,fuel,mpv,smoke,sos,temp, geoFencing;


    public double latitude;
    public double longitude;
    public double parkLatitude;
    public double parkLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        parking_switch = (Switch)findViewById(R.id.parking_switch);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        final String UVID = mFirebaseUser.getUid();



        alert=(TextView)findViewById(R.id.alert_detector);
        fire=(TextView)findViewById(R.id.fire_detector);
        fuel=(TextView)findViewById(R.id.fuel_detector);
        mpv=(TextView)findViewById(R.id.tripped_detector);
        smoke=(TextView)findViewById(R.id.smoke_detector);
        sos=(TextView)findViewById(R.id.sos_detector);
        temp=(TextView)findViewById(R.id.temp_detector);
        geoFencing=(TextView)findViewById(R.id.geofencingTextView);
        alertOff=(Button)findViewById(R.id.alertOffButton);


        alertOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseReference.child(UVID).child("sensor_data").child("sos").setValue("0");
                mDatabaseReference.child(UVID).child("sensor_data").child("alert").setValue("0");
                mDatabaseReference.child(UVID).child("sensor_data").child("fire").setValue("0");
                mDatabaseReference.child(UVID).child("sensor_data").child("smoke").setValue("0");
                mDatabaseReference.child(UVID).child("sensor_data").child("mpv").setValue("0");
            }
        });


        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String UVID = mFirebaseUser.getUid();

                if(dataSnapshot.child(UVID).child("parking_mode").child("mode").getValue().toString().equals("1")){
                    parking_switch.setChecked(true);
                }
                else{
                    parking_switch.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        parking_switch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String UVID = mFirebaseUser.getUid();
                String parking_mode;


                if(parking_switch.isChecked()){
                    parking_mode ="1";
                    mDatabaseReference.child(UVID).child("parking_mode").child("mode").setValue(parking_mode).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Parking mode ON", Toast.LENGTH_SHORT).show();

                            //Apply geofencing measures
                            mDatabaseReference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    String parkLat = dataSnapshot.child(UVID).child("parking_mode").child("latitude").getValue().toString();
                                    String parkLong = dataSnapshot.child(UVID).child("parking_mode").child("longitude").getValue().toString();

                                    String lat = dataSnapshot.child(UVID).child("location").child("latitude").getValue().toString();
                                    String longi = dataSnapshot.child(UVID).child("location").child("longitude").getValue().toString();

                                    latitude = Double.parseDouble(lat);
                                    longitude = Double.parseDouble(longi);
                                    parkLatitude = Double.parseDouble(parkLat);
                                    parkLongitude = Double.parseDouble(parkLong);

                                    Toast.makeText(MainActivity.this, lat, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this, longi, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this, parkLat, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(MainActivity.this, parkLong, Toast.LENGTH_SHORT).show();


                                    double theta = parkLongitude - longitude;
                                    double dist = Math.sin(deg2rad(parkLatitude))
                                            * Math.sin(deg2rad(latitude))
                                            + Math.cos(deg2rad(parkLatitude))
                                            * Math.cos(deg2rad(latitude))
                                            * Math.cos(deg2rad(theta));

                                    dist = Math.acos(dist);
                                    dist = rad2deg(dist);
                                    dist = dist * 60 * 1.1515;


                                    if(dist >= 0.25){
                                        geoFencing.setText("Car Outside geofenced area");
                                    }
                                    else{
                                        geoFencing.setText("Car Inside geofenced area");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });

                        }
                    });
                }
                else{
                    parking_mode="0";
                    mDatabaseReference.child(UVID).child("parking_mode").child("mode").setValue(parking_mode).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(MainActivity.this, "Parking mode OFF", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }



            private double deg2rad(double deg) {
                return (deg * Math.PI / 180.0);
            }

            private double rad2deg(double rad) {
                return (rad * 180.0 / Math.PI);
            }
        });


        accident_database_reference = mFirebaseDatabase.getReference().child("users").child(UVID).child("sensor_data");



        accident_database_reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String fuels = dataSnapshot.child("fuel").getValue().toString();
                String temps = dataSnapshot.child("temp").getValue().toString();
                String smokes = dataSnapshot.child("smoke").getValue().toString();
                String fires = dataSnapshot.child("fire").getValue().toString();
                String tripped = dataSnapshot.child("mpv").getValue().toString();
                String soss = dataSnapshot.child("sos").getValue().toString();
                String alerts = dataSnapshot.child("alert").getValue().toString();

                alert.setText("alert- " + alerts);
                sos.setText("sos- " + soss);
                mpv.setText("tripped- " + tripped);
                fire.setText("fire-" + fires);
                smoke.setText("smoke- " + smokes);
                temp.setText("temp- " + temps);
                fuel.setText("fuel- " + fuels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
