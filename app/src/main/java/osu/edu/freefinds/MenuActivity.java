package osu.edu.freefinds;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/**
 * Main menu
 *
 * Created by stephaniesmacbook on 10/8/17.
 */

public class MenuActivity extends SingleFragmentActivity {
    protected final String TAG = getClass().getSimpleName();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_settings was selected
            case R.id.action_menu:
                Log.d(TAG, "Close menu");
                finish();
                break;
            default:
                break;
        }

        return true;
    }


    @Override
    protected Fragment createFragment() {
        return new MenuFragment();
    }
}
