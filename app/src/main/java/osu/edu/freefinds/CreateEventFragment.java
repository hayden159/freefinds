package osu.edu.freefinds;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Liz on 10/26/2017.
 */

public class CreateEventFragment extends Fragment {
    private EditText titleField;
    private EditText locationField;
    private EditText descriptionField;
    private SeekBar difficultyVal;
    private TimePicker timeField;
    private DatePicker dateField;
    private final String TAG = "CreateEventFragment";

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);

        titleField = (EditText) v.findViewById(R.id.event_name);
        descriptionField = (EditText) v.findViewById(R.id.event_description);
        locationField = (EditText) v.findViewById(R.id.event_location);
        difficultyVal = (SeekBar) v.findViewById(R.id.difficulty_val);
        timeField = (TimePicker) v.findViewById(R.id.event_time);
        dateField = (DatePicker) v.findViewById(R.id.event_date);

        Button dbButton = (Button) v.findViewById(R.id.db_button);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Event myEvent = new Event();

                String titleText = titleField.getText().toString();
                String descriptionText = descriptionField.getText().toString();
                String location = locationField.getText().toString();
                int difficulty = difficultyVal.getProgress();
                int hour = timeField.getHour();
                int minute = timeField.getMinute();
                int year = dateField.getYear();
                int month = dateField.getMonth();
                int dayOfMonth = dateField.getDayOfMonth();

                myEvent.setTitle(titleText);
                myEvent.setDescription(descriptionText);
                myEvent.setOsuLocation(location);
                myEvent.setDifficulty(difficulty);
                myEvent.setHour(hour);
                myEvent.setMinute(minute);
                myEvent.setYear(year);
                myEvent.setMonth(month);
                myEvent.setDayOfMonth(dayOfMonth);

                DatabaseReference events = database.child("events");
                DatabaseReference newEvent = events.push();

                newEvent.setValue(myEvent);

                //leave create event view now that the event is created
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
            }
        });

        return v;
    }
}
