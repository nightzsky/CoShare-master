package com.example.chris.coshare;

import android.support.constraint.solver.widgets.Snapshot;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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

    public Boolean Book(DataSnapshot snapshot, String location, String tableid, long addpoint, String phoneno) { //snapshot at database
        if ((boolean)snapshot.child("Locations").child(location).child(tableid).child("Availability").getValue() == true) { //if table available
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

    public HashMap<ArrayList<String>,Boolean> getEntireTable(DataSnapshot dataSnapshot) {
        HashMap<ArrayList<String>,Boolean> entiretable = new HashMap();
        Iterable<DataSnapshot> locations = dataSnapshot.getChildren();
        for(DataSnapshot location:locations){
            String key1 = location.getKey().toString();
            Iterable<DataSnapshot> tables = location.getChildren();
            for (DataSnapshot table:tables) {
                String key2 = table.getKey().toString();
                ArrayList key = new ArrayList<String>();
                key.add(key1); //locations
                key.add(key2); //table number
                Boolean value = (Boolean) table.child("Availability").getValue(); //status of the table number
                entiretable.put(key,value);
            }
        }
        for (ArrayList<String> name: entiretable.keySet()){
            for(String i:name){
                Log.i("check",i);
            }
            String value = entiretable.get(name).toString();
            Log.i("check",value);
        }
        return entiretable;
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
    //snapshot is with reference to DatabaseReference locationName=DBref.child("Users");
    public long getUserPoints(DataSnapshot dataSnapshot,String phoneNumber){
        long points =(long) dataSnapshot.child(phoneNumber).child("Points").getValue();

        return points;
    }

    public HashMap<String,Long> getLocationsVisited(DataSnapshot dataSnapshot, String phoneNumber){
        HashMap<String,Long> loc=new HashMap();
        Iterable<DataSnapshot> locations=dataSnapshot.child("Locations").getChildren();
        Log.i("check","Triggered");
        for(DataSnapshot dsp : locations){
            String key=dsp.getKey().toString();
            Log.i("check",key);
            Long value=(Long) dsp.getValue();
            loc.put(key,value);
        }
        return loc;
    }
}