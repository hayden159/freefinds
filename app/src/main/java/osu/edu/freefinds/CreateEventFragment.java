package osu.edu.freefinds;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TimePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;

import static android.app.Activity.RESULT_OK;
import static java.lang.Integer.parseInt;

/**
 * Created by Liz on 10/26/2017.
 */

// side todo: get the id from firebase after the event is created

public class CreateEventFragment extends Fragment {
    private EditText titleField;
    private EditText locationField;
    private EditText descriptionField;
    private SeekBar difficultyVal;
    private Button datePickerButton;
    private Button startTimePickerButton;
    private Button endTimePickerButton;
    private final String TAG = "CreateEventFragment";
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    private Bitmap imageBitmap = null;
    private String imageFileName = "";
    private static final String DIALOG_DATE = "DialogDate";
    private static final String DIALOG_TIME = "DialogTime";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_START_TIME = 1;
    private static final int REQUEST_END_TIME = 2;
    static final int REQUEST_IMAGE_CAPTURE = 3;
    private Event myEvent = new Event();


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
        datePickerButton = (Button) v.findViewById(R.id.date_picker_button);
        startTimePickerButton = (Button) v.findViewById(R.id.start_time_picker_button);
        endTimePickerButton = (Button) v.findViewById(R.id.end_time_picker_button);

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

        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setTargetFragment(CreateEventFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        startTimePickerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.setTargetFragment(CreateEventFragment.this, REQUEST_START_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        endTimePickerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                TimePickerFragment dialog = new TimePickerFragment();
                dialog.setTargetFragment(CreateEventFragment.this, REQUEST_END_TIME);
                dialog.show(manager, DIALOG_TIME);
            }
        });

        Button dbButton = (Button) v.findViewById(R.id.db_button);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                String titleText = titleField.getText().toString();
                String descriptionText = descriptionField.getText().toString();
                String location = locationField.getText().toString();
                int difficulty = difficultyVal.getProgress();

                myEvent.setTitle(titleText);
                myEvent.setDescription(descriptionText);
                myEvent.setOsuLocation(location);
                myEvent.setDifficulty(difficulty);
                myEvent.setImageFileName(imageFileName);

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

            String file = generateString(new Random(), 10);
            Log.d(TAG, "Filename " + file);
            imageFileName = file;

            Bundle extras = data.getExtras();
            if(extras != null){
                imageBitmap = (Bitmap) extras.get("data");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageData = baos.toByteArray();
                storageRef.child("images").child(file).putBytes(imageData);
            }
        }

        if (requestCode == REQUEST_DATE && resultCode == RESULT_OK) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            myEvent.setDate(date);
        }

        if (requestCode == REQUEST_START_TIME && resultCode == RESULT_OK) {
            Time time = (Time) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            myEvent.setHour(time.getHour());
            myEvent.setMinute(time.getMinute());
        }

        if (requestCode == REQUEST_END_TIME && resultCode == RESULT_OK) {
            Time time = (Time) data.getSerializableExtra(TimePickerFragment.EXTRA_TIME);
            myEvent.setEndHour(time.getHour());
            myEvent.setEndMinute(time.getMinute());
        }


    }

    public static String generateString(Random rng, int length)
    {
        String characters = "abcdefghijklmnopqrstuvwxyz123456789";
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}
