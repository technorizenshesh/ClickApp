package com.my.clickapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.my.clickapp.R;
import com.my.clickapp.model.NearestShopModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class SeeAllSaloonRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<NearestShopModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public SeeAllSaloonRecyclerViewAdapter(Context context, ArrayList<NearestShopModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<NearestShopModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_see_all_salooon, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final NearestShopModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


          genericViewHolder.txtMassaeg.setText(model.getShopName()+"");
          genericViewHolder.search_home.setText(model.getShopAddress()+"");

          Picasso.get().load(model.getShopImage()).into(genericViewHolder.massage);

        }

    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private NearestShopModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, NearestShopModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtMassaeg;
        private TextView search_home;
        private ImageView massage;


        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtMassaeg=itemView.findViewById(R.id.txtMassaeg);
            this.search_home=itemView.findViewById(R.id.search_home);
            this.massage=itemView.findViewById(R.id.massage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }


}

