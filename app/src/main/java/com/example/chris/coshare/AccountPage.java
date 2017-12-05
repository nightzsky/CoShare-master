package com.example.chris.coshare;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    ArrayList<String> personalDetails=new ArrayList<>();

    TextView welcomeMsg;
    TextView points;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        database= FirebaseDatabase.getInstance();
        DBrefLocations = database.getReference().child("Users");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountpage);

        welcomeMsg=findViewById(R.id.welcomeUser);
        points=findViewById(R.id.totalPointsNum);

        be=new backend();
        DBrefLocations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            personalDetails=be.getPersonalData(dataSnapshot,"83423995");
            name=personalDetails.get(0);
            tableLocation=personalDetails.get(1);
            tableID=personalDetails.get(2);

            userPoints=be.getUserPoints(dataSnapshot,"83423995");

            welcomeMsg.setText("Hello "+name);

            points.setText(Long.toString(userPoints));



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

