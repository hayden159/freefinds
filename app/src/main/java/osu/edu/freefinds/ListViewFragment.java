package osu.edu.freefinds;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by stephaniesmacbook on 10/22/17.
 */

public class ListViewFragment extends Fragment implements View.OnClickListener  {
    public final String TAG = getClass().getSimpleName();
    private PopupWindow pw;
    ListView mSortLevelListView;
    SortingType sType;


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

        FloatingActionButton difficulty = (FloatingActionButton) v.findViewById(R.id.fab_difficutly);
        difficulty.setOnClickListener(this);

        return v;
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_difficutly:
                Log.d(TAG, "difficulty button pressed");
                initiatePopupWindow(v, new Difficulty());
                break;
            case R.id.fab_check:
                Log.d(TAG, "upvote button pressed");
                // initiatePopupWindow(v, new Upvote());

        }
    }


    private void initiatePopupWindow(View v, SortingType s) {
        try {
            sType = s;
            //We need to get the instance of the LayoutInflater, use the context of this activity
            LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.fragment_popup,
                    (ViewGroup) v.findViewById(R.id.popup_element));
            // create a 300px width and 470px height PopupWindow
            pw = new PopupWindow(layout, 400, 470, true);
            // display the popup in the center
            pw.showAtLocation(v, Gravity.CENTER, -50, -140);


            mSortLevelListView = (ListView) layout.findViewById(R.id.sort_level_list_view);
            TextView t = (TextView) layout.findViewById(R.id.Title);
            t.setText("Sort by Difficulty");

            // Adapter
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                    R.layout.list_item_sort_level, R.id.sort_level, sType.getTitles());

            Log.d(TAG, "list view " + mSortLevelListView);


            // Sets the adapter for the ListView
            mSortLevelListView.setAdapter(adapter);

            // ListView Item Click Listener
            mSortLevelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    // ListView Clicked item index
                    int itemPosition     = position;

                    // ListView Clicked item value
                    String  itemValue    = (String) mSortLevelListView.getItemAtPosition(position);

                    // Filter out events
                    FragmentManager cfm = getChildFragmentManager();
                    EventListFragment eventListFragment = (EventListFragment) cfm.findFragmentById(R.id.event_list);

                    sType.handleClick(itemValue, eventListFragment);
                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
