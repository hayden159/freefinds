package osu.edu.freefinds;

import java.util.ArrayList;
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
    private Date mDate;
    private int mEndHour;
    private int mEndMinute;
    private int mUpvote;
    private int mDownvote;
    private String mOsuLocation;
    private int mDifficulty;
    private String mImageFileName;
    private ArrayList<Comment> mComments;
    private String user;

    public Event() {
        //mId is from Firebase - can only retrieve after object is stored
        this.mUpvote = 0;
        this.mDownvote = 0;
        this.mComments = new ArrayList<>();
    }

    public Event(String id, String title, String description, int hour, int minute, Date date, int endHour, int endMinute, String osuLocation, int difficulty){
        this.mId = id;
        this.mTitle = title;
        this.mDescription = description;
        this.mHour = hour;
        this.mMinute = minute;
        this.mDate = date;
        this.mEndHour = endHour;
        this.mEndMinute = endMinute;
        this.mUpvote = 0;
        this.mDownvote = 0;
        this.mOsuLocation = osuLocation;
        this.mDifficulty = difficulty;
        this.mComments = new ArrayList<>();
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

    public int getEndHour() {
        return mEndHour;
    }

    public void setEndHour(int endHour) {
        mEndHour = endHour;
    }

    public int getEndMinute() {
        return mEndMinute;
    }

    public void setEndMinute(int endMinute) {
        mEndMinute = endMinute;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
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

    public String getImageFileName() {
        return mImageFileName;
    }

    public void setImageFileName(String imageFileName) {
        mImageFileName = imageFileName;
    }

    public ArrayList<Comment> getComments() {
        return mComments;
    }

    public void setComments(ArrayList<Comment> comments) {
        mComments = comments;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}
