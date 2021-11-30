package com.my.clickapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.my.clickapp.R;
import com.my.clickapp.model.PaymentModel;
import com.my.clickapp.model.serviceDetailsModel;

import java.util.ArrayList;


public class SelectedServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private ArrayList<PaymentModel.Result> modelList;
    private OnItemClickListener mItemClickListener;

    public SelectedServicesAdapter(Context context, ArrayList<PaymentModel.Result> modelList) {
        this.mContext = context;
        this.modelList = modelList;
    }

    public void updateList(ArrayList<PaymentModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_services_name, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final PaymentModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


         //   String IsFav= model.getIsFav().toString();

           genericViewHolder.txtName.setText(model.getServiceName());
           genericViewHolder.txtPrice.setText(model.getPrice());

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

    private PaymentModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, PaymentModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private TextView txtPrice;
        private CardView cardImg;
        private ImageView imgCircle;

        public ViewHolder(final View itemView) {
            super(itemView);

          this.txtName=itemView.findViewById(R.id.txtName);
          this.txtPrice=itemView.findViewById(R.id.txtPrice);
          this.cardImg=itemView.findViewById(R.id.cardImg);
          this.imgCircle=itemView.findViewById(R.id.imgCircle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }

    }

}

