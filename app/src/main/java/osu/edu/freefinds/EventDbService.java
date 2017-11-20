package osu.edu.freefinds;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stephaniesmacbook on 11/20/17.
 */

public class EventDbService extends Service {

    public static final String TAG = "EventDbService";
    DatabaseReference mRootRef,mEventsRef;
    private List<Event> mEvents = new ArrayList<Event>();


    IBinder mBinder = new LocalBinder();

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public EventDbService getServerInstance() {
            return EventDbService.this;
        }
    }


    @Override
    public void onCreate() {
        Log.d(TAG, "oncreate called");
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mEventsRef = mRootRef.child("events");

        mEventsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Event e = dataSnapshot.getValue(Event.class);
                e.setId(dataSnapshot.getKey());
                mEvents.add(e);
                Log.d(TAG, "Adding child " + dataSnapshot.child("title").getValue());
                Log.d(TAG, "id from firebase is " + dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Event newE = dataSnapshot.getValue(Event.class);
                Event toChange;
                for (Event e : mEvents) {
                    if (e.getId() == newE.getId()) {
                        toChange = e;
                    }
                }
                toChange = newE;
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public List<Event> getDbEvents() {
        return mEvents;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onstartcommand");


        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestory called");
    }
}
