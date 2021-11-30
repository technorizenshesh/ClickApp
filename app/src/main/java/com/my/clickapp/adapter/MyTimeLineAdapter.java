package com.my.clickapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.HomeDetails;
import com.my.clickapp.act.VideoPlayActivity;
import com.my.clickapp.model.VideoListModel;
import com.my.clickapp.model.serviceDetailsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MyTimeLineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<VideoListModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public MyTimeLineAdapter(Context context, ArrayList<VideoListModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<VideoListModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final VideoListModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtView.setText(model.getTotalView()+" Views");

            genericViewHolder.txtDuration.setText(model.getDuration()+"");

            genericViewHolder.txtLIke.setText(model.getTotalLike()+" Like");

             if(model.getType().equalsIgnoreCase("Image"))
             {
                 genericViewHolder.RRImg.setVisibility(View.GONE);
                 genericViewHolder.RRVideoImg.setVisibility(View.GONE);

                 Picasso.get().load(model.getVideo()).into(genericViewHolder.imgEVideo);

             }else
             {
                 genericViewHolder.RRImg.setVisibility(View.VISIBLE);

                 genericViewHolder.RRVideoImg.setVisibility(View.VISIBLE);

                 Glide.with(mContext).load(model.getVideo()).centerCrop()
                         .into(genericViewHolder.imgEVideo);

                // Picasso.get().load(model.getVideo()).into(genericViewHolder.imgEVideo);
             }

             genericViewHolder.RRVideoImg.setOnClickListener(v -> {

                 Preference.save(mContext, Preference.KEY_Video_id,model.getId());

                 mContext.startActivity(new Intent(mContext, VideoPlayActivity.class).putExtra("link",model.getVideo()));

             });




        }
    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private VideoListModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, VideoListModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout RRVideoImg;
        private ImageView RRImg;
        private ImageView imgEVideo;
        private TextView txtView;
        private TextView txtDuration;
        private TextView txtLIke;


        public ViewHolder(final View itemView) {
            super(itemView);

          this.RRVideoImg=itemView.findViewById(R.id.RRVideoImg);
        this.RRImg=itemView.findViewById(R.id.RRImg);
          this.imgEVideo=itemView.findViewById(R.id.imgEVideo);
          this.txtView=itemView.findViewById(R.id.txtView);
          this.txtDuration=itemView.findViewById(R.id.txtDuration);
          this.txtLIke=itemView.findViewById(R.id.txtLIke);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }

    }

}

