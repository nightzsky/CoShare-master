package com.example.chris.coshare;

import android.support.constraint.solver.widgets.Snapshot;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by chris on 5/12/2017.
 */

public class backend {
    FirebaseDatabase database;
    DatabaseReference DBrefUsers;
    DatabaseReference DBrefLocations;


    public backend() {
        database = FirebaseDatabase.getInstance();
        DBrefUsers = database.getReference().child("Locations");
        DBrefLocations = database.getReference().child("Users");
    }

    public String getName(String phoneNumber) {
        String name = DBrefUsers.child(phoneNumber).child("Owner Name").toString();
        return name;
    }

    public Boolean Book(DataSnapshot snapshot, String location, String tableid, int addpoint) {
        if(snapshot.child(location).child(tableid).child("Availability").toString()=="True"){ //if table available
            DBrefLocations.child(location).child(tableid).child("Availability").setValue("False");  //not available anymore
            DBrefLocations.child(location).child(tableid).child("Occupant").setValue(DBrefUsers.child("UserID(Phone number)").toString()); //fill in booker
            DBrefUsers.child("UserID(Phone number)").child("Booking Status").setValue("False"); //set user booking status to booked
            int points = Integer.parseInt(DBrefUsers.child("UserID(Phone number)").child("Points").toString().trim());
            points += addpoint; //yay gain points
            DBrefUsers.child("UserID(Phone number)").child("Points").setValue(points); //update points
            return true;
        }
        else{
            return false; //seperate function to notify user "oh no it has booked"
        }
    }

    public ArrayList<String> getPersonalData(DataSnapshot dataSnapshot, String phoneNumber){
        ArrayList<String> personalinfo=new ArrayList<>();
        String name=dataSnapshot.child(phoneNumber).child("Owner Name").getValue().toString();
        String PersonaltableLocation=dataSnapshot.child(phoneNumber).child("Table Info").child("Location").getValue().toString();
        String PersonaltableID=dataSnapshot.child(phoneNumber).child("TableID").getValue().toString();
        String PersononalBookingStatus=dataSnapshot.child(phoneNumber).child("Booking Status").getValue().toString();
        personalinfo.add(name);
        personalinfo.add(PersonaltableLocation);
        personalinfo.add(PersonaltableID);
        personalinfo.add(PersononalBookingStatus);

        return personalinfo;
    }

    //snapshot is with reference to DatabaseReference locationName=DBref.child("Locations");
    public String getOwnTableStatus(DataSnapshot dataSnapshot, String phoneNumber){


    }
}
