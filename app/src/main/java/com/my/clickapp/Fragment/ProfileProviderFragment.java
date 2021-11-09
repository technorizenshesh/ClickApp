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
import com.my.clickapp.act.ChooseAdminUser;
import com.my.clickapp.act.EditProfile;
import com.my.clickapp.act.LoginActivity;
import com.my.clickapp.act.ReviewActivity;
import com.my.clickapp.act.actProvider.Addvideo;
import com.my.clickapp.databinding.FragmentProfileBinding;
import com.my.clickapp.databinding.FragmentProfileProviderBinding;

public class ProfileProviderFragment extends Fragment {


    FragmentProfileProviderBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile_provider, container, false);

      binding.lleditProfile.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),  EditProfile.class));

      });

      binding.txtAddVideo.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(), Addvideo.class));

      });

      binding.llReview.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),  ReviewActivity.class));

      });

      binding.btnLogout.setOnClickListener(v ->{

          startActivity(new Intent(getActivity(),ChooseAdminUser.class));
             getActivity().finish();
      });

        return binding.getRoot();
    }
}