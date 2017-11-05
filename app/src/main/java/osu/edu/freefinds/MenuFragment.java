package osu.edu.freefinds;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import static com.facebook.FacebookSdk.getApplicationContext;


/**
 * Created by stephaniesmacbook on 10/8/17.
 */

public class MenuFragment extends Fragment implements View.OnClickListener  {
    public final String TAG = getClass().getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Menu");
        actionBar.show();

        View v = inflater.inflate(R.layout.fragment_menu, container, false);

        Button createEvent = (Button) v.findViewById(R.id.button1);
        createEvent.setOnClickListener(this);
        Button eventList = (Button) v.findViewById(R.id.button2);
        eventList.setOnClickListener(this);
        Button myEvents = (Button) v.findViewById(R.id.button4);
        myEvents.setOnClickListener(this);
        Button login = (Button) v.findViewById(R.id.button6);
        login.setOnClickListener(this);
        Button logout = (Button) v.findViewById(R.id.button7);
        logout.setOnClickListener(this);

        return v;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(getActivity().getApplicationContext(), CreateEventActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(getActivity().getApplicationContext(), MainActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(getActivity().getApplicationContext(), ViewSingleEventActivity.class));
                break;
            case R.id.button7:
                LoginManager.getInstance().logOut();
                Context context = getApplicationContext();
                CharSequence text = "Logged out";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                break;
        }
    }

    }
