package osu.edu.freefinds;

import android.app.Instrumentation;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import android.view.View;
import android.widget.Button;

/**
 * Created by stephaniesmacbook on 11/20/17.
 */

public class MenuTest extends ActivityInstrumentationTestCase2<MenuActivity> {
private MenuActivity mMenuActivity;
private MenuFragment mMenuFragment;

public final String TAG = getClass().getSimpleName();


public MenuTest() {
        super(MenuActivity.class);
        }

@Override
protected void setUp() throws Exception {
        super.setUp();

    mMenuActivity = getActivity();
    mMenuFragment = new MenuFragment();
    mMenuActivity.getSupportFragmentManager()
            .beginTransaction()
            .add(R.id.fragment_container, mMenuFragment, null)
            .commit();




        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();

        setActivityInitialTouchMode(false);
        }

@Test
public void testPreconditions() {
        assertNotNull(mMenuActivity);
        assertNotNull(mMenuFragment);
        }

@UiThreadTest
public void testEventListClick () {
final Button eventList = (Button) mMenuActivity.findViewById(R.id.button2);
        Instrumentation.ActivityMonitor activityMonitor = getInstrumentation()
            .addMonitor(MainActivity.class.getName(), null, false);
        mMenuActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    eventList.performClick();
            }
        });
        MainActivity mainActivity = (MainActivity) activityMonitor.waitForActivity();

        assertNotNull("Target Activity is not launched", mainActivity);


}


    protected void tearDown() throws Exception { // cleans up
        mMenuActivity.finish();
        super.tearDown();
    }
}
