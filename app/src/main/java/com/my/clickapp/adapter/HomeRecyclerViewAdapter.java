package com.my.clickapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.my.clickapp.AcceptClickListener;
import com.my.clickapp.R;
import com.my.clickapp.model.AllOrdermOdel;
import com.my.clickapp.model.HomeModel;

import java.util.ArrayList;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AlertDialog alertDialog;
    private Button btn_no;
    private Button btn_yes;
    private Context mContext;
    /* access modifiers changed from: private */
    public OnItemClickListener mItemClickListener;
    /* access modifiers changed from: private */
    public ArrayList<AllOrdermOdel.Result> modelList;

    AcceptClickListener acceptClickListener;



    public interface OnItemClickListener {
        void onItemClick(View view, int i, AllOrdermOdel.Result teamsModel);
    }

    public HomeRecyclerViewAdapter(Context context, ArrayList<AllOrdermOdel.Result> modelList2,AcceptClickListener acceptClickListener) {
        this.mContext = context;
        this.modelList = modelList2;
        this.acceptClickListener = acceptClickListener;
    }

    public void updateList(ArrayList<AllOrdermOdel.Result> modelList2) {
        this.modelList = modelList2;
        notifyDataSetChanged();
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home, viewGroup, false));
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            AllOrdermOdel.Result item = getItem(position);
            ViewHolder viewHolder = (ViewHolder) holder;

            viewHolder.txtName.setText(item.getUserDetails().getName());
            viewHolder.txtPrice.setText("$"+item.getPrice());

            if(item.getUserDetails().getImage()!=null)
            {
                Glide.with(mContext).load(item.getUserDetails().getImage()).into(viewHolder.imgUser);
            }

            String Services1="";

            if(item.getServicesDetails()!=null)
            {
                int Size= item.getServicesDetails().size();

                for(int i=0;i<Size;i++)
                {
                   String Services =item.getServicesDetails().get(i)+",";
                    Services1 =Services1+Services;
                }
                viewHolder.txtServices.setText(Services1);
            }


            viewHolder.txtAccept.setOnClickListener(v -> {

                acceptClickListener.onItemClick(item.getId(),"Accepted");

            });

            viewHolder.txtReject.setOnClickListener(v -> {

                acceptClickListener.onItemClick(item.getId(),"Rejected");
            });

        }
    }

    public int getItemCount() {
        return this.modelList.size();
    }

    public void SetOnItemClickListener(OnItemClickListener mItemClickListener2) {
        this.mItemClickListener = mItemClickListener2;
    }

    private AllOrdermOdel.Result getItem(int position) {
        return this.modelList.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtName;
        TextView txtServices;
        TextView txtPrice;
        TextView txtAccept;
        TextView txtReject;
        ImageView imgUser;

        public ViewHolder(final View itemView) {
            super(itemView);

            this.txtName=itemView.findViewById(R.id.txtName);
            this.txtServices=itemView.findViewById(R.id.txtServices);
            this.txtPrice=itemView.findViewById(R.id.txtPrice);
            this.txtAccept=itemView.findViewById(R.id.txtAccept);
            this.txtReject=itemView.findViewById(R.id.txtReject);
            this.imgUser=itemView.findViewById(R.id.imgUser);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}
