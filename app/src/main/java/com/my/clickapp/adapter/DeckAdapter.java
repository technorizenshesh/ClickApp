package com.my.clickapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.HomeDetails;
import com.my.clickapp.model.CourseModal;

import java.util.ArrayList;

public class DeckAdapter extends BaseAdapter {

    // on below line we have created variables
    // for our array list and context.
    private ArrayList<CourseModal.Result> courseData;
    private Context context;

    // on below line we have created constructor for our variables.
    public DeckAdapter(ArrayList<CourseModal.Result> courseData, Context context) {
        this.courseData = courseData;
        this.context = context;
    }

    @Override
    public int getCount() {
        // in get count method we are returning the size of our array list.
        return courseData.size();
    }

    @Override
    public Object getItem(int position) {
        // in get item method we are returning the item from our array list.
        return courseData.get(position);
    }

    @Override
    public long getItemId(int position) {
        // in get item id we are returning the position.
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // in get view method we are inflating our layout on below line.
        View v = convertView;
        if (v == null) {
            // on below line we are inflating our layout.
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_rv_item, parent, false);
        }
        // on below line we are initializing our variables and setting data to our variables.

        TextView txtMassaeg=(TextView) v.findViewById(R.id.txtMassaeg);
        TextView Address=(TextView) v.findViewById(R.id.Address);
        ImageView img=(ImageView) v.findViewById(R.id.idIVCourse);

        txtMassaeg.setText(courseData.get(position).getShopDetails().getShopName());
        Address.setText(courseData.get(position).getShopDetails().getShopAddress());

        ((ImageView) v.findViewById(R.id.imgCross)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){



            }
        });

        ((ImageView) v.findViewById(R.id.imgCheck)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preference.save(context,Preference.KEY_id,courseData.get(position).getShopDetails().getId());

                context.startActivity(new Intent(context, HomeDetails.class));

            }
        });

        return v;
    }
}
