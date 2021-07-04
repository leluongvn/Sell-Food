package com.example.maket.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maket.Entity.FeedBack;
import com.example.maket.R;

import java.util.List;
import java.util.zip.Inflater;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.viewHolderFeedback> {
    Context mContext;
    List<FeedBack> feedBacks;

    public FeedBackAdapter(Context mContext, List<FeedBack> feedBacks) {
        this.mContext = mContext;
        this.feedBacks = feedBacks;
    }

    @NonNull
    @Override
    public viewHolderFeedback onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_feedback, parent, false);
        viewHolderFeedback feedback = new viewHolderFeedback(view);
        return feedback;
    }

    @Override
    public void onBindViewHolder(FeedBackAdapter.viewHolderFeedback holder, int position) {
        FeedBack feedBack = feedBacks.get(position);
        holder.mTextView.setText(feedBack.getContent());
        holder.mTextViewStar.setText(feedBack.getStar()+"sao");

    }

    @Override
    public int getItemCount() {
        return feedBacks.size();
    }


    public class viewHolderFeedback extends RecyclerView.ViewHolder {
        TextView mTextView;
        TextView mTextViewStar;

        public viewHolderFeedback(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tvContent);
            mTextViewStar = itemView.findViewById(R.id.tvStar);
        }
    }
}
