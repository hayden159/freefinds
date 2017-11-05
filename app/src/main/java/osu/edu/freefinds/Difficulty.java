package osu.edu.freefinds;

import android.app.FragmentManager;

import java.lang.reflect.Array;

/**
 * Created by stephaniesmacbook on 10/26/17.
 */

public class Difficulty extends SortingType {
    public static String TITLE = "Difficulty";
    public String mDescription;
    public String mTitle;
    public static String[] mTitles = new String[] {
                "Easy",
                "Medium",
                "Hard"
    };
    public static String[] mDescriptions = new String[]{
            "This event requires almost nothing. You might need to provide your email " +
                    "address or listen to a very short speech, but the total time is less than five minutes.",

            "This event requires moderate effort. You might need to listen to a longer speech " +
                    "or participate in an activity. The total time is more than 5 minutes but less than 20 minutes.",

            "This event will take some time. You might need to fill out a long survey " +
                    "or sit in on a long talk. This activity takes longer than 20 minutes."
    };
    public static int sortingCategoriesLength = mTitles.length;

    public Difficulty(){
    }

    @Override
    public String[] getTitles() {
        return mTitles;
    }

    private void filterDifficulty(int level, EventListFragment e) {
        Event filterEvent = new Event();
        filterEvent.setDifficulty(level);
        e.filterEvents(filterEvent);
    }

    @Override
    public void handleClick(String itemClicked, EventListFragment e) {
        switch (itemClicked) {
            case "Easy" :
                filterDifficulty(0, e);
                break;
            case "Medium" :
                filterDifficulty(1, e);
                break;
            case "Hard" :
                filterDifficulty(2, e);
                break;

        }
    }


    public void setLevel(int level) {
        if (level>=0 && level<sortingCategoriesLength) {
            setFields(level);
        } else {
            setFields(0);
        }
    }

    private void setFields(int level) {
        mDescription = mDescriptions[level];
        mTitle = mTitles[level];
    }

}
