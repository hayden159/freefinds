package osu.edu.freefinds;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Event Singleton
 *
 * Created by stephaniesmacbook on 10/25/17.
 *
 */

public class CommentLab {
    private static CommentLab sCommentLab;
    private static String key;

    DatabaseReference mRootRef, mCommentsRef;

    private List<Comment> mComments;

    public static final String TAG = "CommentLab";



    public static CommentLab get(Context context, String keyVal) {
        key = keyVal;
        if (sCommentLab == null) {
            sCommentLab = new CommentLab(context);
        }

        return sCommentLab;
    }

    private Comment generateSampleComment() {
        Comment mC1 = new Comment();
        mC1.setContent("This was a great event!");
        mC1.setHourPosted(11);
        mC1.setMinutePosted(0);
        return mC1;
    }

    private CommentLab(Context context) {
        mComments = new ArrayList<Comment>();
        mRootRef = FirebaseDatabase.getInstance().getReference();
        mCommentsRef = mRootRef.child("events").child(key).child("comments");
        Log.d(TAG, "mCommentsRef: " + mCommentsRef.toString());

        mCommentsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                Comment e = dataSnapshot.getValue(Comment.class);
                Log.d(TAG, "Comment found: " + e.toString());
                e.setId(dataSnapshot.getKey());
                mComments.add(e);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {}

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


    public List<Comment> getComments() {
        return mComments;
    }

    public Comment getEvent(String id) {
        for (Comment event : mComments) {
            if (event.getId().equals(id)) {
                return event;
            }
        }

        return null;
    }
}
