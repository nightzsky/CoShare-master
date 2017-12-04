package com.example.chris.coshare;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by chris on 5/12/2017.
 */

public class backend {
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;


    public backend(){
        database = FirebaseDatabase.getInstance();
        DBrefUsers=database.getReference().child("Locations");
        DBrefLocations=database.getReference().child("Users");
    }
    public String getName(String phoneNumber){
        String name=DBrefUsers.child(phoneNumber).child("Owner Name").toString();
        return name;
    }
}
