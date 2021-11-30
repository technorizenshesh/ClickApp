package com.my.clickapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.ReviewActivity;
import com.my.clickapp.model.HomeModel;
import com.my.clickapp.model.MyUserBookingModel;

import java.util.ArrayList;
import java.util.List;

public class MyUserBookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AlertDialog alertDialog;

    private Context mContext;
    public OnItemClickListener mItemClickListener;
    public ArrayList<MyUserBookingModel.Result> modelList;

    public interface OnItemClickListener {
        void onItemClick(View view, int i, MyUserBookingModel.Result teamsModel);
    }

    public MyUserBookingAdapter(Context context, ArrayList<MyUserBookingModel.Result> modelList2) {
        this.mContext = context;
        this.modelList = modelList2;
    }

    public void updateList(ArrayList<MyUserBookingModel.Result> modelList2) {
        this.modelList = modelList2;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_booking, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            MyUserBookingModel.Result model = getItem(position);
            ViewHolder genericViewHolder = (ViewHolder) holder;

            genericViewHolder.txtShopName.setText(model.getShopDetails().getShopName());

            genericViewHolder.txtStatus.setText(model.getStatus());

            if(model.getStatus().equalsIgnoreCase("Accepted"))
            {
                genericViewHolder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorGreen));

            }else if(model.getStatus().equalsIgnoreCase("Pending"))
            {
                genericViewHolder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorBackgroundBg));

            }else
            {
                genericViewHolder.txtStatus.setTextColor(ContextCompat.getColor(mContext, R.color.teal_700));

            }


            genericViewHolder.txtPrice.setText("$ "+model.getPrice());

            List<String> Serviceslist = new ArrayList<>();

            if(model.getServicesDetails()!=null)
            {
                for (int i = 0; i < model.getServicesDetails().size(); i++)
                {
                        String services_Name = model.getServicesDetails().get(i);
                        Serviceslist.add(services_Name);
                }

                String services_name = TextUtils.join(",",Serviceslist);

                genericViewHolder.txtServicesName.setText(services_name+"");

            }

            genericViewHolder.txtWrite.setOnClickListener(v -> {
                Preference.save(mContext, Preference.KEY_Provider_id,model.getProviderId());
                mContext.startActivity(new Intent(mContext, ReviewActivity.class));
            });

        }
    }

    public int getItemCount() {
        return this.modelList.size();
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener2) {
        this.mItemClickListener = mItemClickListener2;
    }

    private MyUserBookingModel.Result getItem(int position) {
        return this.modelList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtShopName;
        TextView txtServicesName;
        TextView txtOrderId;
        TextView txtStatus;
        TextView txtPrice;
        TextView txtWrite;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtShopName=itemView.findViewById(R.id.txtShopName);
            this.txtServicesName=itemView.findViewById(R.id.txtServicesName);
            this.txtOrderId=itemView.findViewById(R.id.txtOrderId);
            this.txtStatus=itemView.findViewById(R.id.txtStatus);
            this.txtPrice=itemView.findViewById(R.id.txtPrice);
            this.txtWrite=itemView.findViewById(R.id.txtWrite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));

                }
            });
        }
    }
}
