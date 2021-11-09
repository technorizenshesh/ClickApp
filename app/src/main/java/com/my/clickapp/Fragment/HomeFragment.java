package com.my.clickapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.my.clickapp.R;
import com.my.clickapp.act.HomeDetails;
import com.my.clickapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);

        binding.imgRight.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), HomeDetails.class));

        });

        return binding.getRoot();
    }




}