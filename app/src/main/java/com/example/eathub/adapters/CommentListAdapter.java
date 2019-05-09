package com.example.eathub.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eathub.R;
import com.example.eathub.models.VisitModel;

import java.util.List;

public class CommentListAdapter extends BaseAdapter {
    private Context context;
    private List<VisitModel> commentList;


    public CommentListAdapter(Context context, List<VisitModel> commentList){
        this.context = context;
        this.commentList = commentList;
    }

    @Override
    public int getCount() {
        return this.commentList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.commentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (commentList!=null) {
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.comment_list_item, null);
            }
            VisitModel visitModel = (VisitModel) this.getItem(position);
            TextView rate = view.findViewById(R.id.rate);
            rate.setText(""+visitModel.getMark());

            ImageView avatarConnected = view.findViewById(R.id.avatarConnected);
            avatarConnected.setImageResource(view.getResources().getIdentifier(visitModel.getProfileModel().getName()
                    .replaceAll(" ", "").replaceAll("-", "").toLowerCase(), "drawable", context.getPackageName()));

            TextView comment = view.findViewById(R.id.comment);
            comment.setText(visitModel.getCommentary());
        }
        return view;

    }
}
