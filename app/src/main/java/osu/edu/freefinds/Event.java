package osu.edu.freefinds;

import java.util.Date;
import java.util.UUID;

/**
 * Created by stephaniesmacbook on 10/25/17.
 */

public class Event {

    // definitely don't have all the fields yet
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private int mUpvote;
    private int mDownvote;

    public Event() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }


    // Getters and setters

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
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
