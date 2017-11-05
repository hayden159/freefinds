package osu.edu.freefinds;


        import android.app.FragmentManager;

        import java.lang.reflect.Array;

/**
 * Created by stephaniesmacbook on 10/26/17.
 */

public class Upvote extends SortingType {
    public static String TITLE = "Upvote";
    public String mDescription;
    public String mTitle;
    public static String[] mTitles = new String[] {
            "At least one upvote",
            "At least five upvotes",
            "More than ten upvotes"
    };

    public static int sortingCategoriesLength = mTitles.length;

    public Upvote(){
    }

    @Override
    public String[] getTitles() {
        return mTitles;
    }

    private void filterUpvote(int level, EventListFragment e) {
        Event filterEvent = new Event();
        filterEvent.setUpvote(level);
        e.filterEvents(filterEvent);
    }

    @Override
    public void handleClick(String itemClicked, EventListFragment e) {
        switch (itemClicked) {
            case "At least one upvote" :
                filterUpvote(1, e);
                break;
            case "At least five upvotes" :
                filterUpvote(5, e);
                break;
            case "More than ten upvotes" :
                filterUpvote(10, e);
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
        mTitle = mTitles[level];
    }

}
