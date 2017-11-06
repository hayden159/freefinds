package osu.edu.freefinds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by stephaniesmacbook on 10/25/17.
 */

public class EventListFragment extends Fragment {
    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;
    private List<Event> mUiEvents;
    private View mView;


    public static final String TAG = "EventListFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_event_list, container, false);

        mEventRecyclerView = (RecyclerView) mView
                .findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();
        return mView;

    }

    public void updateUI() {
          EventLab eventLab = EventLab.get(getActivity());
          mUiEvents = eventLab.getEvents();
          mAdapter = new EventAdapter(mUiEvents);
          mEventRecyclerView.setAdapter(mAdapter);
    }

    public void filterEvents(Event hasFilteringSet) {
        EventLab eventLab = EventLab.get(getActivity());
        mUiEvents = eventLab.getEventsFiltered(hasFilteringSet);
        mAdapter = new EventAdapter(mUiEvents);
        mEventRecyclerView.setAdapter(mAdapter);
    }



    private class EventHolder extends RecyclerView.ViewHolder  implements View.OnClickListener  {
        private TextView mTitleTextView;
        private TextView mAttTextView;
        private TextView mDateTextView;
        private TextView mUpvoteView;


        private Event mEvent;


        @Override
        public void onClick(View view) {
            // inflate a single activity view and pass in mEvent
            Intent intent = new Intent(getActivity().getBaseContext(), ViewSingleEventActivity.class);
            intent.putExtra("single_event_id", mEvent.getId());
            startActivity(intent);

        }

        public void bind(Event event) {
            mEvent = event;
            mTitleTextView.setText(mEvent.getTitle());
            mAttTextView.setText(mEvent.getDescription());
            mUpvoteView.setText(String.valueOf(mEvent.getUpvote()));

            // format time
            Date date = event.getDate();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String fDate = formattedDate(month, day);
            String fTime = formattedTime(event.getHour(), event.getEndMinute());

            mDateTextView.setText(fTime + "\n" + fDate);
        }

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mAttTextView = (TextView) itemView.findViewById(R.id.event_attributes);
            mDateTextView = (TextView) itemView.findViewById(R.id.time);
            mUpvoteView = (TextView) itemView.findViewById(R.id.upvote_count);

        }

    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder> {

        private List<Event> mEvents;

        public EventAdapter(List<Event> events) {
            mEvents = events;
        }

        @Override
        public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EventHolder holder, int position) {
            Event event = mEvents.get(position);
            holder.bind(event);
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }

    private String formattedTime(int hoursNum, int minutesNum){
        String hours;
        String minutes;
        String ampm = "am";

        if(hoursNum>12){
            hours = Integer.toString(hoursNum-12);
            ampm = "pm";
        }else if(hoursNum==0){
            hours = "12";
        }else{
            hours = Integer.toString(hoursNum);
        }

        if(minutesNum==0){
            minutes = "00";
        }else if(minutesNum<10) {
            minutes = "0" + Integer.toString(minutesNum);
        }else{
            minutes = Integer.toString(minutesNum);
        }

        return hours + ":" + minutes + " " + ampm;
    }

    private String formattedDate(int monthNum, int dayOfMonthNum){
        String month = "";
        switch (monthNum) {
            case 0:
                month = "January";
                break;
            case 1:
                month = "February";
                break;
            case 2:
                month = "March";
                break;
            case 3:
                month = "April";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "August";
                break;
            case 8:
                month = "September";
                break;
            case 9:
                month = "October";
                break;
            case 10:
                month = "November";
                break;
            case 11:
                month = "December";
                break;
        }

        return month + " " + Integer.toString(dayOfMonthNum);
    }



}


