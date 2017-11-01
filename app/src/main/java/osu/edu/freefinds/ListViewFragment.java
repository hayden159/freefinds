package osu.edu.freefinds;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by stephaniesmacbook on 10/22/17.
 */

public class ListViewFragment extends Fragment implements View.OnClickListener  {
    public final String TAG = getClass().getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setTitle("Free stuff near Ohio State University");
        actionBar.show();

        View v = inflater.inflate(R.layout.fragment_list_view, container, false);

        FloatingActionButton refresh = (FloatingActionButton) v.findViewById(R.id.fab_refresh);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Refresh button pressed");
                FragmentManager cfm = getChildFragmentManager();
                EventListFragment listFragment = (EventListFragment) cfm.findFragmentById(R.id.event_list);
                listFragment.updateUI();
            }
        });


        return v;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            // todo: handle sorting buttons
//            case R.id.button1:
//                // startActivity(new Intent(getActivity().getApplicationContext(), GameSessionActivity.class));
//                break;

        }
    }

}
