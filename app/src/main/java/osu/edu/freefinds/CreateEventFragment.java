package osu.edu.freefinds;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private final String TAG = "CreateEventFragment";

    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_event, container, false);

        titleField = (EditText) v.findViewById(R.id.event_name);
        titleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                //blank
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        Button dbButton = (Button) v.findViewById(R.id.db_button);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleText = titleField.getText().toString();

                database.child("eventName").setValue(titleText);
            }
        });

        final TextView dbShow = (TextView) v.findViewById(R.id.db_show);

        database.child("eventName").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String appTitle = dataSnapshot.getValue().toString();
                dbShow.setText(appTitle);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e("Hey", "Failed to read app title value.", error.toException());
            }
        });

        return v;
    }
}
