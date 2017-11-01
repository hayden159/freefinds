package osu.edu.freefinds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Event Singleton
 *
 * Created by stephaniesmacbook on 10/25/17.
 *
 */

public class EventLab extends Activity{
    private static EventLab sEventLab;

    DatabaseReference mRootRef,mEventsRef;

    private List<Event> mEvents;

    public static final String TAG = "EventsLab";



    public static EventLab get(Context context) {
        if (sEventLab == null) {
            sEventLab = new EventLab(context);
        }

        return sEventLab;
    }

    private Event generateSampleEvent() {
        Event mE1 = new Event();
        mE1.setTitle("Free Finds Sample Event");
        mE1.setMonth(1);
        mE1.setDayOfMonth(12);
        mE1.setYear(2018);
        mE1.setHour(12);
        mE1.setMinute(30);
        mE1.setDescription("Spin for water bottle, kind bar, magnet.");
        mE1.setDifficulty(1);
        mE1.setUpvote(3);
        mE1.setOsuLocation("Rpac Lower Level");
        return mE1;
    }

    private EventLab(Context context) {
        mEvents = new ArrayList<Event>();
    }


    public List<Event> getEvents() {

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mEventsRef = mRootRef.child("events");

        mEventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Log.d(TAG, "Adding child " + dataSnapshot.child("title").getValue());
                Log.d(TAG, "id from firebase is " + dataSnapshot.getKey());
                Event e = dataSnapshot.getValue(Event.class);
                e.setId(dataSnapshot.getKey());
                mEvents.add(e);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        if (mEvents.size()==0) {
            Event sample = generateSampleEvent();
            mEvents.add(sample);
        }


        return mEvents;
    }

    public Event getEvent(String id) {
        for (Event event : mEvents) {
            if (event.getId().equals(id)) {
                return event;
            }
        }

        return null;
    }
}
