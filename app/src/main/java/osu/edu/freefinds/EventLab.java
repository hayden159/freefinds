package osu.edu.freefinds;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.io.Serializable;
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

public class EventLab extends Activity {
    private static EventLab sEventLab;

    boolean mBounded;
    EventDbService mdBServer;

    private List<Event> mEvents;

    public static final String TAG = "EventsLab";

    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "Service is disconnected");
            mBounded = false;
            mdBServer = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "Service is connected");
            mBounded = true;
            EventDbService.LocalBinder mLocalBinder = (EventDbService.LocalBinder)service;
            mdBServer = mLocalBinder.getServerInstance();
        }
    };




    public static EventLab get(Context context) {
        if (sEventLab == null) {
            sEventLab = new EventLab(context);
        }

        return sEventLab;
    }


    private EventLab(Context context) {
        mEvents = new ArrayList<Event>();
    }



    public void initializeListener(Context mContext) {
        Intent dbIntent;

        Log.d(TAG, mContext.getPackageName()+ " @ "+ EventDbService.class );
        dbIntent = new Intent(mContext,
                EventDbService.class);
        mContext.startService(dbIntent);
        mContext.bindService(dbIntent, mConnection, BIND_AUTO_CREATE);

    }




    public List<Event> getEvents() {
        Log.d(TAG,"called getEvents");
        if (mdBServer != null) {
            mEvents = mdBServer.getDbEvents();
            Collections.sort(mEvents, new Comparator<Event>() {
                public int compare(Event o1, Event o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
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

    public List<Event> getEventsFiltered(Event hasFilteringSet) {
        List<Event> mFilteredEvents = new ArrayList<Event>();

        for (Event e : mEvents) {
            boolean filteredOut = false;
            if (hasFilteringSet.getDifficulty() != null) {
                if (hasFilteringSet.getDifficulty() != e.getDifficulty()) {
                    filteredOut = true;
                }
            }
            if (!filteredOut && hasFilteringSet.getUpvote() != null) {
                if (hasFilteringSet.getUpvote() >= e.getUpvote()) {
                    filteredOut = true;
                }
            }

            if (!filteredOut && hasFilteringSet.getDate() != null) {
                Date startDate = hasFilteringSet.getDate();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                Date endDate = calendar.getTime();

                if(e.getDate().after(endDate) || e.getDate().before(startDate)) {
                    filteredOut = true;
                }
            }

            if (!filteredOut) {
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


    @Override
    protected void onStop() {
        super.onStop();
        if(mBounded) {
            unbindService(mConnection);
            mBounded = false;
        }
    };
}
