package com.my.clickapp.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.my.clickapp.LikeDisllikeClickListener;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.HomeDetails;
import com.my.clickapp.act.ReportActivity;
import com.my.clickapp.act.VideoPlayActivity;
import com.my.clickapp.model.serviceDetailsModel;
import com.my.clickapp.utils.RetrofitClients;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ShopVideoImgageListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<serviceDetailsModel.Result.ShopVideo> modelList;
    private OnItemClickListener mItemClickListener;

    LikeDisllikeClickListener likeDisllikeClickListener;

    public ShopVideoImgageListRecyclerViewAdapter(Context context, ArrayList<serviceDetailsModel.Result.ShopVideo> modelList, LikeDisllikeClickListener likeDisllikeClickListener) {
        this.mContext = context;
        this.modelList = modelList;
        this.likeDisllikeClickListener = likeDisllikeClickListener;
    }

    public void updateList(ArrayList<serviceDetailsModel.Result.ShopVideo> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_video_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final serviceDetailsModel.Result.ShopVideo model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;




            genericViewHolder.txtView.setText(model.getTotalView()+" Views");

            genericViewHolder.txtDuration.setText(model.getDuration()+"");

            genericViewHolder.txtLIke.setText(model.getLiked()+" Like");

            String UserLike=model.getLiked().toString();

            if(UserLike.equalsIgnoreCase("0"))
            {
                genericViewHolder.Imglike.setVisibility(View.GONE);


            }else
            {
               // genericViewHolder.Imglike.setImageResource(R.drawable.ciclr_green);
                genericViewHolder.Imglike.setVisibility(View.VISIBLE);
            }

            genericViewHolder.RRLikeDislike.setOnClickListener(v ->{

                likeDisllikeClickListener.onItemClick(model.getId());

            });

            genericViewHolder.RRVideoImg.setOnClickListener(v ->{

                Preference.save(mContext,Preference.KEY_Video_id,model.getId());
                mContext.startActivity(new Intent(mContext, VideoPlayActivity.class).putExtra("link",model.getVideo()));

            });

            genericViewHolder.txtReport.setOnClickListener(v ->{

               mContext.startActivity(new Intent(mContext, ReportActivity.class));

            });


           // Picasso.get().load(model.getImage()).into(genericViewHolder.img1);
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private serviceDetailsModel.Result.ShopVideo getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, serviceDetailsModel.Result.ShopVideo model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtView;
        private TextView txtLIke;
        private TextView txtDuration;
        private TextView txtReport;
        private RelativeLayout RRLikeDislike;
        private RelativeLayout RRVideoImg;
        private ImageView Imglike;

        public ViewHolder(final View itemView) {
            super(itemView);

          this.txtView=itemView.findViewById(R.id.txtView);
          this.txtDuration=itemView.findViewById(R.id.txtDuration);
          this.txtLIke=itemView.findViewById(R.id.txtLIke);
          this.RRLikeDislike=itemView.findViewById(R.id.RRLikeDislike);
          this.Imglike=itemView.findViewById(R.id.Imglike);
          this.txtReport=itemView.findViewById(R.id.txtReport);
          this.RRVideoImg=itemView.findViewById(R.id.RRVideoImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }




}

