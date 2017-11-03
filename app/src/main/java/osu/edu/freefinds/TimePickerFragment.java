package osu.edu.freefinds;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

/**
 * Created by Liz on 11/3/2017.
 */

public class TimePickerFragment extends DialogFragment {

    private static final String ARG_TIME = "time";

    private TimePicker mTimePicker;

    public static final String EXTRA_TIME =
            "osu.edu.freefind.android.time";

    public static TimePickerFragment newInstance(Time time) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);

        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_time, null);

        mTimePicker = v.findViewById(R.id.event_time);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.time_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Time myTime = new Time(mTimePicker.getHour(), mTimePicker.getMinute());
                                sendResult(Activity.RESULT_OK, myTime);
                            }
                        })
                .create();
    }

    private void sendResult(int resultCode, Time time) {
        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TIME, time);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}