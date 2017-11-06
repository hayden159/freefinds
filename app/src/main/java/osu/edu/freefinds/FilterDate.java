package osu.edu.freefinds;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by stephaniesmacbook on 10/26/17.
 */

public class FilterDate extends SortingType {
    private static String SORTING_TYPE_NAME = "Date";
    public String mTitle;
    public static String[] mTitles = new String[] {
            "This week",
            "Next week",
            "Two weeks from now",
            "Three weeks from now"
    };

    public static int sortingCategoriesLength = mTitles.length;

    public FilterDate(){
    }

    @Override
    public String[] getTitles() {
        return mTitles;
    }

    private void filterDate(int noOfDays, EventListFragment e) {
        Event filterEvent = new Event();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new java.util.Date());
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        Date date = calendar.getTime();
        filterEvent.setDate(date);
        e.filterEvents(filterEvent);
    }

    @Override
    public void handleClick(String itemClicked, EventListFragment e) {
        switch (itemClicked) {
            case "This week" :
                filterDate(0, e);
                break;
            case "Next week" :
                filterDate(7, e);
                break;
            case "Two weeks from now" :
                filterDate(14, e);
                break;
            case "Three weeks from now" :
                filterDate(21, e);
                break;

        }
    }

    @Override
    public String getSortingTypeName() {
        return SORTING_TYPE_NAME;
    }


    public void setLevel(int level) {
        if (level>=0 && level<sortingCategoriesLength) {
            setFields(level);
        } else {
            setFields(0);
        }
    }

    private void setFields(int level) {
        mTitle = mTitles[level];
    }

}
