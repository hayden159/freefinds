package osu.edu.freefinds;

import java.util.Date;
import java.util.UUID;

/**
 * Created by stephaniesmacbook on 10/25/17.
 */

public class Event {
    private String mId;
    private String mTitle;
    private String mDescription;
    private int mHour;
    private int mMinute;
    private int mYear;
    private int mMonth;
    private int mDayOfMonth;
    private int mUpvote;
    private int mDownvote;
    private String mOsuLocation;
    private int mDifficulty;

    public Event() {
        //mId is from Firebase - can only retrieve after object is stored
        mUpvote = 0;
        mDownvote = 0;
    }

    public Event(String id, String title, String description, int hour, int minute, int year, int month, int dayOfMonth, String osuLocation, int difficulty){
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mHour = hour;
        this.mMinute = minute;
        this.mYear = year;
        this.mMonth = month;
        this.mDayOfMonth = dayOfMonth;
        this.mUpvote = 0;
        this.mDownvote = 0;
        this.mOsuLocation = osuLocation;
        this.mDifficulty = difficulty;
    }

    // Getters and setters

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getHour() {
        return mHour;
    }

    public void setHour(int hour) {
        mHour = hour;
    }

    public int getMinute() {
        return mMinute;
    }

    public void setMinute(int minute) {
        mMinute = minute;
    }

    public int getYear() {
        return mYear;
    }

    public void setYear(int year) {
        mYear = year;
    }

    public int getMonth() {
        return mMonth;
    }

    public void setMonth(int month) {
        mMonth = month;
    }

    public int getDayOfMonth() {
        return mDayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        mDayOfMonth = dayOfMonth;
    }

    public String getOsuLocation() {
        return mOsuLocation;
    }

    public void setOsuLocation(String mOsuLocation) {
        this.mOsuLocation = mOsuLocation;
    }

    public int getDifficulty() {
        return mDifficulty;
    }

    public void setDifficulty(int mdifficulty) {
        this.mDifficulty = mdifficulty;
    }

    public String getId() {
        return mId;
    }
    public void setId(String id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }
    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public int getUpvote() {
        return mUpvote;
    }

    public void setUpvote(int mUpvote) {
        this.mUpvote = mUpvote;
    }

    public int getDownvote() {
        return mDownvote;
    }

    public void setDownvote(int mDownvote) {
        this.mDownvote = mDownvote;
    }

}