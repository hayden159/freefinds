package osu.edu.freefinds;

import android.content.Context;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Date;


/**
 * Event Singleton
 *
 * Created by stephaniesmacbook on 10/25/17.
 *
 */

public class EventLab {
    private static EventLab sEventLab;

    private List<Event> mEvents;
    
    public static EventLab get(Context context) {
        if (sEventLab == null) {
            sEventLab = new EventLab(context);
        }
        return sEventLab;
    }

    private Event generateSampleEvent() {
        Event mE1 = new Event();
        mE1.setTitle("OSU Chaarge: Find out who we are!");
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
        mEvents.add(generateSampleEvent());
        for (int i = 0; i < 13; i++) {
            Event event = new Event();
            event.setTitle("Event #" + i);
            mEvents.add(event);
        }
    }


    public List<Event> getEvents() {
        return mEvents;
    }

    public Event getEvent(UUID id) {
        for (Event event : mEvents) {
            if (event.getId().equals(id)) {
                return event;
            }
        }

        return null;
    }
}
