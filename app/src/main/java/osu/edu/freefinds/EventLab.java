package osu.edu.freefinds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
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


    private EventLab(Context context) {
        mEvents = new ArrayList<Event>();
    }


    public List<Event> getEvents() {

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mEventsRef = mRootRef.child("events");

        mEventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Event e = dataSnapshot.getValue(Event.class);
                e.setId(dataSnapshot.getKey());
                boolean found = false;
                for (Event existingE : mEvents) {
                    if (existingE.getId().equals(e.getId())) {
                        found = true;
                        break;
                    }
                }

                if(!found) {
                    mEvents.add(e);
                    Log.d(TAG, "Adding child " + dataSnapshot.child("title").getValue());
                    Log.d(TAG, "id from firebase is " + dataSnapshot.getKey());
                }


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


        Collections.sort(mEvents, new Comparator<Event>() {
            public int compare(Event o1, Event o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

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

    public List<Event> getEventsFiltered(Event hasFilteringSet) {
        List<Event> mFilteredEvents = new ArrayList<Event>();

        for (Event e : mEvents) {
            if (hasFilteringSet.getDifficulty() != null) {
                if (hasFilteringSet.getDifficulty() == e.getDifficulty()) {
                    mFilteredEvents.add(e);
                }
            } else {
                mFilteredEvents.add(e);
            }
        }
        Collections.sort(mFilteredEvents, new Comparator<Event>() {
            public int compare(Event o1, Event o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        return mFilteredEvents;
    }
}
