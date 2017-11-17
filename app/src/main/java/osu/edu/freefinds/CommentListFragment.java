package osu.edu.freefinds;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.List;


/**
 * Created by stephaniesmacbook on 10/25/17.
 */

public class CommentListFragment extends Fragment {
    private RecyclerView mCommentRecyclerView;
    private CommentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment_list, container, false);

        mCommentRecyclerView = (RecyclerView) view
                .findViewById(R.id.comment_recycler_view);
        mCommentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;

    }

    private void updateUI() {
        String key = getActivity().getIntent().getStringExtra("single_event_id");
        CommentLab commentLab = CommentLab.get(getActivity(), key);
        List<Comment> comments = commentLab.getComments();
        mAdapter = new CommentAdapter(comments);
        mCommentRecyclerView.setAdapter(mAdapter);
    }



    private class CommentHolder extends RecyclerView.ViewHolder {
        private TextView mContentTextView;
        private TextView mTimeTextView;


        private Comment mComment;

        public void bind(Comment comment) {
            mComment = comment;
            mContentTextView.setText(mComment.getContent());
            mTimeTextView.setText(formattedTime(mComment.getHourPosted(), mComment.getMinutePosted()));
        }

        public CommentHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_comment, parent, false));
            mContentTextView = (TextView) itemView.findViewById(R.id.comment_text);
            mTimeTextView = (TextView) itemView.findViewById(R.id.comment_time);
        }
    }

    private class CommentAdapter extends RecyclerView.Adapter<CommentHolder> {

        private List<Comment> mComments;

        public CommentAdapter(List<Comment> comments) {
            mComments = comments;
        }

        @Override
        public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new CommentHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(CommentHolder holder, int position) {
            Comment comment = mComments.get(position);
            holder.bind(comment);
        }

        @Override
        public int getItemCount() {
            return mComments.size();
        }
    }

    private String formattedTime(int hoursNum, int minutesNum){
        String hours;
        String minutes;
        String ampm = "am";

        if(hoursNum>12){
            hours = Integer.toString(hoursNum-12);
            ampm = "pm";
        }else if(hoursNum==0){
            hours = "12";
        }else{
            hours = Integer.toString(hoursNum);
        }

        if(minutesNum==0){
            minutes = "00";
        }else if(minutesNum<10) {
            minutes = "0" + Integer.toString(minutesNum);
        }else{
            minutes = Integer.toString(minutesNum);
        }

        return hours + ":" + minutes + " " + ampm;
    }

}
