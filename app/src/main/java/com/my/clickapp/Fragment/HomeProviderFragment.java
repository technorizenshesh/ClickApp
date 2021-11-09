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
import com.my.clickapp.adapter.HomeRecyclerViewAdapter;
import com.my.clickapp.databinding.FragmentHomeBinding;
import com.my.clickapp.databinding.FragmentHomeProviderBinding;
import com.my.clickapp.model.HomeModel;

import java.util.ArrayList;


public class HomeProviderFragment extends Fragment {


    FragmentHomeProviderBinding binding;

    HomeRecyclerViewAdapter mAdapter;
    private ArrayList<HomeModel> modelList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_provider, container, false);

        setAdapter();

        return binding.getRoot();

    }

    private void setAdapter() {

        modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));

        mAdapter = new HomeRecyclerViewAdapter(getActivity(),modelList);
        binding.recyclerReequuest.setHasFixedSize(true);
        binding.recyclerReequuest.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerReequuest.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
            public void onItemClick(View view, int position, HomeModel model) {

            }
        });
    }

}