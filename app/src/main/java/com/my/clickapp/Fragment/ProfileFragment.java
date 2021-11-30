package com.my.clickapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.ChooseAdminUser;
import com.my.clickapp.act.EditProfile;
import com.my.clickapp.act.LoginActivity;
import com.my.clickapp.act.MyBookingUser;
import com.my.clickapp.act.ReviewActivity;
import com.my.clickapp.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        String  User_name = Preference.get(getActivity(),Preference.KEY_User_name);
        String  User_Mobile = Preference.get(getActivity(),Preference.KEY_mobile);
        String  UserImg = Preference.get(getActivity(),Preference.KEY_USer_img);


        binding.nAme.setText(User_name);

        binding.txtMobile.setText(User_Mobile+"");

        if(!UserImg.equalsIgnoreCase(""))
        {
            Glide.with(this).load(UserImg).into(binding.imgUser);
        }

       binding.lleditProfile.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),  EditProfile.class));
      });

      binding.llReview.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),  ReviewActivity.class));

      });

      binding.txtMyBooking.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),  MyBookingUser.class));

      });

      binding.btnLogout.setOnClickListener(v ->{

          Preference.clearPreference(getActivity());

          startActivity(new Intent(getActivity(), ChooseAdminUser.class));
             getActivity().finish();

      });

        return binding.getRoot();
    }
}