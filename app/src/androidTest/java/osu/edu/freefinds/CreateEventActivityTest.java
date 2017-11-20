package osu.edu.freefinds;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.annotation.UiThreadTest;
import android.test.ActivityInstrumentationTestCase2;

import org.junit.Test;

import android.widget.Button;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class CreateEventActivityTest extends ActivityInstrumentationTestCase2<CreateEventActivity> {
    private CreateEventActivity mCreateEventActivity;
    private CreateEventFragment mCreateEventFragment;

    public CreateEventActivityTest() {
        super(CreateEventActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mCreateEventActivity = getActivity();
        mCreateEventFragment = new CreateEventFragment();
        mCreateEventActivity.getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mCreateEventFragment, null)
                .commit();


        // Wait for the Activity to become idle so we don't have null Fragment references.
        getInstrumentation().waitForIdleSync();

        setActivityInitialTouchMode(false);
    }

    @Test
    public void testPreconditions() {
        assertNotNull(mCreateEventActivity);
        assertNotNull(mCreateEventFragment);
    }

    @UiThreadTest
    public void testDoesNotCreateEventWithoutDateOrTimeSet() {
        final Button createEventButton = (Button) mCreateEventActivity.findViewById(R.id.db_button);
        mCreateEventActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // click button and open next activity.
                createEventButton.performClick();
            }
        });
        assertEquals(false, mCreateEventActivity.isDestroyed());
    }

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("osu.edu.freefinds", appContext.getPackageName());
    }
}
