package com.my.clickapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.my.clickapp.R;
import com.my.clickapp.adapter.MyBookingRecyclerViewAdapter;
import com.my.clickapp.databinding.FragmentMybookingBinding;
import com.my.clickapp.model.HomeModel;

import java.util.ArrayList;


public class MyBookingFragment extends Fragment {


    FragmentMybookingBinding binding;

    MyBookingRecyclerViewAdapter mAdapter;
    private ArrayList<HomeModel> modelList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mybooking, container, false);

        setAdapter();

        return binding.getRoot();

    }

    private void setAdapter() {

        modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));

        mAdapter = new MyBookingRecyclerViewAdapter(getActivity(),modelList);
        binding.recyclerBooking.setHasFixedSize(true);
        binding.recyclerBooking.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerBooking.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new MyBookingRecyclerViewAdapter.OnItemClickListener() {
            public void onItemClick(View view, int position, HomeModel model) {

            }
        });
    }

}