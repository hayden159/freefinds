package osu.edu.freefinds;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.TimeZone;


/**
 * Created by Liz on 11/1/2017.
 */

public class ViewSingleEventFragment extends android.support.v4.app.Fragment {

    Event event;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private StorageReference storage = FirebaseStorage.getInstance().getReference();
    String key;
    TextView eventTitleField;
    TextView eventDescriptionField;
    TextView eventLocationField;
    TextView eventTimeField;
    TextView eventDateField;
    TextView eventDurationField;
    ImageView imageView;
    Button upvoteButton;
    Button downvoteButton;
    TextView upvoteField;
    TextView downvoteField;
    TextView difficultyField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_single_event, container, false);

        Bundle b = getActivity().getIntent().getExtras();
        int value = -1; // or other values
        if(b != null) {
            value = b.getInt("key");
        }

        //TODO: this is for testing--remove this
        key = "-KxzGLxEFEHVjJu7FLek";

        DatabaseReference ref = database.child("events").child(key);

        eventTitleField = (TextView) v.findViewById(R.id.event_title);
        eventDescriptionField = (TextView) v.findViewById(R.id.event_description);
        eventLocationField = (TextView) v.findViewById(R.id.event_loc);
        eventTimeField = (TextView) v.findViewById(R.id.event_time);
        eventDateField = (TextView) v.findViewById(R.id.event_date);
        imageView = (ImageView) v.findViewById(R.id.event_image);
        upvoteButton = (Button) v.findViewById(R.id.event_upvote_inc);
        downvoteButton = (Button) v.findViewById(R.id.event_downvote_inc);
        upvoteField = (TextView) v.findViewById(R.id.upvote_show);
        downvoteField = (TextView) v.findViewById(R.id.downvote_show);
        difficultyField = (TextView) v.findViewById(R.id.event_difficulty);

        database.child("events").child(key).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   event = dataSnapshot.getValue(Event.class);
                   populateView(event);
               }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
           });

        upvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentVote = event.getUpvote();
                event.setUpvote(currentVote + 1);
                upvoteField.setText(Integer.toString(event.getUpvote()));
                database.child("events").child(key).child("upvote").setValue(event.getUpvote());
                upvoteButton.setEnabled(false);
                downvoteButton.setEnabled(false);
            }
        });

        downvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentVote = event.getDownvote();
                event.setDownvote(currentVote + 1);
                downvoteField.setText(Integer.toString(event.getDownvote()));
                database.child("events").child(key).child("downvote").setValue(event.getDownvote());
                upvoteButton.setEnabled(false);
                downvoteButton.setEnabled(false);
            }
        });

        return v;
    }

    private void populateView(Event event){
        String title = event.getTitle();
        String description = event.getDescription();
        String location = event.getOsuLocation();
        String upvote = Integer.toString(event.getUpvote());
        String downvote = Integer.toString(event.getDownvote());
        String difficulty = Integer.toString(event.getDifficulty()+1);

        StorageReference imageRef = storage.child("images").child(event.getImageFileName());
        Glide.with(this /* context */)
                .using(new FirebaseImageLoader())
                .load(imageRef)
                .into(imageView );

        eventTitleField.setText(title);
        eventDescriptionField.setText(description);
        eventLocationField.setText(location);
        eventDateField.setText(formattedDate(event.getYear(), event.getMonth(), event.getDayOfMonth()));
        eventTimeField.setText(formattedTime(event.getHour(), event.getMinute()) + " to " +
                formattedTime(event.getEndHour(), event.getEndMinute()));
        upvoteField.setText(upvote);
        downvoteField.setText(downvote);
        difficultyField.setText("Difficulty: " + difficulty + " out of 3");
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
        }else{
            minutes = Integer.toString(minutesNum);
        }

        return hours + ":" + minutes + " " + ampm;
    }

    private String formattedDate(int yearNum, int monthNum, int dayOfMonthNum){
        String month = "";
        switch (monthNum) {
            case 1:
                month = "January";
                break;
            case 2:
                month = "February";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month = "September";
                break;
            case 10:
                month = "October";
                break;
            case 11:
                month = "November";
                break;
            case 12:
                month = "December";
                break;
        }

        return month + " " + Integer.toString(dayOfMonthNum) + ", " + Integer.toString(yearNum);
    }

}
