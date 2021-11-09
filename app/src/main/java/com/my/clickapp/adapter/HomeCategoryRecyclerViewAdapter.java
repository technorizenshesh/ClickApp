package com.my.clickapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.my.clickapp.R;
import com.my.clickapp.model.CategoryModel;
import com.my.clickapp.model.HomeModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeCategoryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    int pos = 0;
    private Context mContext;
    private ArrayList<CategoryModel.Result> modelList;
    private OnItemClickListener mItemClickListener;
    private Fragment fragment;
    boolean isLike=true;

    public HomeCategoryRecyclerViewAdapter(Context context, ArrayList<CategoryModel.Result> modelList, Fragment fragment) {
        this.mContext = context;
        this.modelList = modelList;
        this.fragment = fragment;
    }

    public void updateList(ArrayList<CategoryModel.Result> modelList) {
        this.modelList = modelList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        //Here you can fill your row view
        if (holder instanceof ViewHolder) {
            final CategoryModel.Result model = getItem(position);
            final ViewHolder genericViewHolder = (ViewHolder) holder;


         //   String IsFav= model.getIsFav().toString();

            genericViewHolder.txtName.setText(model.getName());

            Picasso.get().load(model.getImage()).into(genericViewHolder.img1);

        }

    }


    @Override
    public int getItemCount() {

        return modelList.size();
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    private CategoryModel.Result getItem(int position) {
        return modelList.get(position);
    }


    public interface OnItemClickListener {

        void onItemClick(View view, int position, CategoryModel.Result model);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtName;
        private ImageView img1;

        public ViewHolder(final View itemView) {
            super(itemView);
            this.txtName=itemView.findViewById(R.id.txtName);
            this.img1=itemView.findViewById(R.id.img1);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(itemView, getAdapterPosition(), modelList.get(getAdapterPosition()));
                }
            });
        }
    }


}

