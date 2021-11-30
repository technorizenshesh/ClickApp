package com.my.clickapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.model.GetRewiewList;
import com.my.clickapp.model.VideoListModel;
import com.my.clickapp.model.WriteModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class WriteReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<GetRewiewList.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public WriteReviewAdapter(Context context, ArrayList<GetRewiewList.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<GetRewiewList.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_write_review, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final GetRewiewList.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtView.setText(model.getUserDetails().getName()+"");
            genericViewHolder.txtShopDesc.setText(model.getReview()+"");

            genericViewHolder.rat.setRating(Float.parseFloat(model.getRating()));




        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private GetRewiewList.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, GetRewiewList.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private TextView txtView;
        private TextView txtShopDesc;
        private RatingBar rat;

        public ViewHolder(final View itemView) {
            super(itemView);

          this.txtView=itemView.findViewById(R.id.txtView);
          this.txtShopDesc=itemView.findViewById(R.id.txtShopDesc);
          this.rat=itemView.findViewById(R.id.rat);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }

    }

}

