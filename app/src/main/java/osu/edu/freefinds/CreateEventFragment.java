package osu.edu.freefinds;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.graphics.Bitmap;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Liz on 10/26/2017.
 */

// side todo: get the id from firebase after the event is created

public class CreateEventFragment extends Fragment {
    private EditText titleField;
    private EditText locationField;
    private EditText descriptionField;
    private SeekBar difficultyVal;
    private TimePicker timeField;
    private DatePicker dateField;
    private final String TAG = "CreateEventFragment";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private String currentPhotoPath;
    private String titleText = "";
    private String fileName = "";
    private Bitmap imageBitmap = null;
    private ImageView imageView = null;


    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    private FirebaseStorage storage = FirebaseStorage.getInstance();

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

        Button photoButton = (Button) v.findViewById(R.id.photo_button);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( ContextCompat.checkSelfPermission( getActivity(), Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( getActivity(), new String[] {  Manifest.permission.CAMERA  }, MY_PERMISSIONS_REQUEST_CAMERA );
                } else {
                    takePhoto();
                }
            }
        });

        Button dbButton = (Button) v.findViewById(R.id.db_button);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {

                Event myEvent = new Event();

                titleText = titleField.getText().toString();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    takePhoto();
                } else {
                    //permission denied
                }
                return;
            }
        }
    }

    public void takePhoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.d(TAG, "inside onActivityResult()");
            StorageReference storageRef = storage.getReference();

            String eventTitle = titleField.getText().toString();
            String file = eventTitle.replaceAll(" ", "_").toLowerCase() + ".uri";
            Log.d(TAG, "Filename " + file);

            Bundle extras = data.getExtras();
            if(extras != null){
                imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();
                storageRef.child("images").child(file).putBytes(imageData);
            }

        }
    }
}
