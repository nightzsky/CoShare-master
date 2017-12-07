package com.example.chris.coshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Michelle on 12/5/2017.
 */

public class AccountPage extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;
    backend be;

    String name;
    String tableLocation;
    String tableID;
    long userPoints;
    ArrayList<String> personalDetails;
    HashMap<String,Long> location;
    String phoneNumber;
    TextView welcomeMsg;
    TextView points;
    TextView locationsVisited;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountpage);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        phoneNumber="83423995";
        database= FirebaseDatabase.getInstance();
        DBrefLocations = database.getReference().child("Users");
        personalDetails=new ArrayList<>();
        // location=new HashMap<>();

        welcomeMsg=findViewById(R.id.welcomeUser);
        points=findViewById(R.id.totalPointsNum);
        locationsVisited=findViewById(R.id.tablesSharedNum);

        be=new backend();
        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                personalDetails=be.getPersonalData(dataSnapshot,phoneNumber);
                name=personalDetails.get(0);
                tableLocation=personalDetails.get(1);
                tableID=personalDetails.get(2);

                //set user's points
                userPoints=be.getUserPoints(dataSnapshot,phoneNumber);

                //set hello message
                welcomeMsg.setText("Hello "+name);

                points.setText(Long.toString(userPoints));

                location=be.getLocationsVisited(dataSnapshot,phoneNumber);
                Long totalLocations=0L;
                for(String i:location.keySet()){
                    totalLocations=totalLocations+location.get(i);
                }
                locationsVisited.setText(Long.toString(totalLocations));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

