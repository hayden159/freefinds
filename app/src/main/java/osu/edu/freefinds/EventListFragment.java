package osu.edu.freefinds;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.List;


/**
 * Created by stephaniesmacbook on 10/25/17.
 */

public class EventListFragment extends Fragment {
    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        mEventRecyclerView = (RecyclerView) view
                .findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        
        return view;

    }

    private void updateUI() {
          EventLab eventLab = EventLab.get(getActivity());
          List<Event> events = eventLab.getEvents();
          mAdapter = new EventAdapter(events);
          mEventRecyclerView.setAdapter(mAdapter);
    }



    private class EventHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTextView;
        private TextView mAttTextView;
        private TextView mDateTextView;


        private Event mEvent;

        public void bind(Event event) {
            mEvent = event;
            mTitleTextView.setText(mEvent.getTitle());
            mAttTextView.setText(mEvent.getDescription());
            mDateTextView.setText(mEvent.getHour() + ":" + mEvent.getMinute());
        }

        public EventHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mAttTextView = (TextView) itemView.findViewById(R.id.event_attributes);
            mDateTextView = (TextView) itemView.findViewById(R.id.time);
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

}
