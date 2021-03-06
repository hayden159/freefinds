package osu.edu.freefinds;

import android.app.FragmentManager;

/**
 * Created by stephaniesmacbook on 11/3/17.
 */

public abstract class SortingType {
    private  String SORTING_TYPE_NAME;
    public int sortingCategoriesLength;

    public abstract String[] getTitles();
    public abstract void handleClick(String itemClicked, EventListFragment e);
    public abstract String getSortingTypeName();
}
