package osu.edu.freefinds;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by stephaniesmacbook on 11/20/17.
 */

public class MenuTest extends ActivityInstrumentationTestCase2<MenuActivity> {
private MenuActivity mMenuActivity;
private android.app.Fragment mMenuFragment;

public final String TAG = getClass().getSimpleName();


public MenuTest() {
        super(MenuActivity.class);
        }

@Override
protected void setUp() throws Exception {
        super.setUp();

    mMenuActivity = getActivity();



        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();

        setActivityInitialTouchMode(false);
        }

@Test
public void testActivityCreated() {
        assertNotNull(mMenuActivity);
}




    protected void tearDown() throws Exception { // cleans up
        mMenuActivity.finish();
        super.tearDown();
    }
}
