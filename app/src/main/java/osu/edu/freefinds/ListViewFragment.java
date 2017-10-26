package osu.edu.freefinds;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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



        return v;
    }


    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.button1:
//                // startActivity(new Intent(getActivity().getApplicationContext(), GameSessionActivity.class));
//                break;

        }
    }

}
