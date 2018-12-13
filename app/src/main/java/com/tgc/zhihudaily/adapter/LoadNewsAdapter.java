package com.tgc.zhihudaily.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tgc.zhihudaily.R;

import java.util.ArrayList;
import java.util.List;

public class LoadNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<String> titleList = new ArrayList<>();
    private List<String> imageList = new ArrayList<>();
    private Context context;

    public void updateData(Context context, List<String> titleList, List<String> imageList) {
        this.context = context;
        this.titleList.addAll(titleList);
        this.imageList.addAll(imageList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.title.setText(titleList.get(position));
        Glide.with(context).load(imageList.get(position)).into(viewHolder.image);
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title);
            image = itemView.findViewById(R.id.news_image);
        }
    }
}
