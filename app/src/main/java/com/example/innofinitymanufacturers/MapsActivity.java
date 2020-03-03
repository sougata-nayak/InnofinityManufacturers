package com.example.innofinitymanufacturers;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public double latitude;
    public double longitude;

    Toolbar toolbar;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String UVID = mFirebaseUser.getUid();


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("users").child(UVID).child("location");



        toolbar = (Toolbar)findViewById(R.id.toolbarMain);
        toolbar.setTitle("Innofinity");
        toolbar.setTitleTextColor(0XFFFFFFFF);


        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.material_drawer_shadow_top)
                .addProfiles(
                        new ProfileDrawerItem().withName("Innofinity").withEmail("sougatanayak007@gmail.com").withIcon(getResources().getDrawable(R.drawable.logo))
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();


        //if you want to update the items at a later time it is recommended to keep it in a variable
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(1).withName("Home page");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(2).withName("Emergency Contacts");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(3).withName("Open Maps");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(4).withName("View Details");
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(5).withName("Sign Out");

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(headerResult)
                .withToolbar(toolbar)
                .addDrawerItems(
                        item1, item2, item3, item4, item5
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D

                        switch(position){
                            case 1:
                                startActivity(new Intent(MapsActivity.this, MainActivity.class));
                                break;
                            case 2:
                                startActivity(new Intent(MapsActivity.this, DisplayContactsActivity.class));
                                break;
                            case 3:
                                startActivity(new Intent(MapsActivity.this, MapsActivity.class));
                                break;
                            case 4:
                                startActivity(new Intent(MapsActivity.this, DisplayInfoActivity.class));
                                break;
                            case 5:
                                mFirebaseAuth.signOut();
                                finish();
                                startActivity(new Intent(MapsActivity.this, LoginActivity.class));
                                break;
                        }

                        return true;
                    }
                })
                .build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String lat = dataSnapshot.child("latitude").getValue().toString();
                String longi = dataSnapshot.child("longitude").getValue().toString();

                latitude = Double.parseDouble(lat);
                longitude = Double.parseDouble(longi);

                Toast.makeText(MapsActivity.this, latitude + " " + longitude, Toast.LENGTH_LONG).show();


                LatLng location = new LatLng(latitude, longitude);
                mMap.addMarker(new MarkerOptions().position(location).title("Your vehicle's current location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
