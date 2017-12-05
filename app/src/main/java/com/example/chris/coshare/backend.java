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

   /* public Boolean Book(DataSnapshot snapshot, String location, String tableid, int addpoint, String phoneno) { //snapshot at database
        if (snapshot.child("Locations").child(location).child(tableid).child("Availability").getValue() == true) { //if table available
            DBrefLocations.child(location).child(tableid).child("Availability").setValue(false);  //not available anymore
            DBrefLocations.child(location).child(tableid).child("Occupant").setValue(DBrefUsers.child(phoneno).toString()); //fill in booker
            DBrefUsers.child(phoneno).child("Booking Status").setValue("booked"); //set user booking status to booked
            int points = (int) snapshot.child("Users").child(phoneno).child("Points").getValue();
            points += addpoint; //yay gain points
            DBrefUsers.child(phoneno).child("Points").setValue(points); //update points
            int locationcount = (int) snapshot.child("Users").child(phoneno).child("Locations").child(location).getValue();//retrieve location count
            locationcount += 1; //increase by 1
            DBrefUsers.child(phoneno).child("Locations").child(location).setValue(locationcount);//update locationcount
            return true;
        } else {
            return false; //seperate function to notify user "oh no it has booked"
        }
    }
    */

    public ArrayList<String> getPersonalData(DataSnapshot dataSnapshot, String phoneNumber) {
        ArrayList<String> personalinfo = new ArrayList<>();
        String name = dataSnapshot.child(phoneNumber).child("Owner Name").getValue().toString();
        String PersonaltableLocation = dataSnapshot.child(phoneNumber).child("Table Info").child("Location").getValue().toString();
        String PersonaltableID = dataSnapshot.child(phoneNumber).child("Table Info").child("TableID").getValue().toString();
        String PersononalBookingStatus = dataSnapshot.child(phoneNumber).child("Booking Status").getValue().toString();
        personalinfo.add(name);
        personalinfo.add(PersonaltableLocation);
        personalinfo.add(PersonaltableID);
        personalinfo.add(PersononalBookingStatus);
        return personalinfo;
    }

    public String[][] getEntireTable() {
        return null;
    }

    //snapshot is with reference to DatabaseReference locationName=DBref.child("Locations");
    public String getOwnTableCurrentUser(DataSnapshot dataSnapshot, String phoneNumber, String tableLocation, String tableID, String PersonalBookingStatus) {
        String currentUser = null;
        boolean currentStatus = (boolean) dataSnapshot.child(phoneNumber).child(tableLocation).child(tableID).child("Availability").getValue();
        if (PersonalBookingStatus == "booked" && currentStatus == true) {
            currentUser = dataSnapshot.child(phoneNumber).child(tableLocation).child(tableID).child("Occupant").getValue().toString();
        }
        return currentUser;

    }
    //snapshot is with reference to DatabaseReference locationName=DBref.child("Locations");
    public long getUserPoints(DataSnapshot dataSnapshot,String phoneNumber){
        long points =(long) dataSnapshot.child(phoneNumber).child("Points").getValue();

        return points;
    }
}