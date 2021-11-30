package com.my.clickapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.ChooseAdminUser;
import com.my.clickapp.act.EditProfile;
import com.my.clickapp.act.actProvider.Addvideo;
import com.my.clickapp.act.actProvider.MyTimeLine;
import com.my.clickapp.act.actProvider.WriteReviewListProvider;
import com.my.clickapp.databinding.FragmentProfileProviderBinding;

public class ProfileProviderFragment extends Fragment {

    private Fragment fragment;

    FragmentProfileProviderBinding binding;
    FragmentManager fragmentManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_provider, container, false);

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

          startActivity(new Intent(getActivity(),EditProfile.class));

      });

      binding.myTImeLine.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),MyTimeLine.class));

      });

      binding.txtAddVideo.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),Addvideo.class));

      });

      binding.llReview.setOnClickListener(v ->{

         // startActivity(new Intent(getActivity(),ReviewActivity.class));
          startActivity(new Intent(getActivity(), WriteReviewListProvider.class));

      });

      binding.btnLogout.setOnClickListener(v ->{

          Preference.clearPreference(getActivity());

          startActivity(new Intent(getActivity(),ChooseAdminUser.class));
           getActivity().finish();

      });

      binding.MyBooking.setOnClickListener(v ->{

          fragment = new MyBookingFragment();
          loadFragment(fragment);

      });

        return binding.getRoot();
    }

    public boolean loadFragment(Fragment fragment){
        fragmentManager = getActivity().getSupportFragmentManager();
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .addToBackStack("Home")
                    .replace(R.id.fragment_homeContainer, fragment)//, tag)
                    .commit();
            return true;
        }
        return false;
    }
}