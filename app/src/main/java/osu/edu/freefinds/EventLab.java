package osu.edu.freefinds;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    private EventLab(Context context) {
        mEvents = new ArrayList<Event>();
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
