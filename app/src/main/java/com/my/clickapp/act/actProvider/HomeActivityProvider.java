package com.my.clickapp.act.actProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.my.clickapp.Fragment.HomeProviderFragment;
import com.my.clickapp.Fragment.MyBookingFragment;
import com.my.clickapp.Fragment.ProfileProviderFragment;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityHomeBinding;
import com.my.clickapp.databinding.ActivityHomeProviderBinding;


public class HomeActivityProvider extends AppCompatActivity {

    private Fragment fragment;

    ActivityHomeProviderBinding binding;

    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_provider);

        binding.RRHome.setOnClickListener(view -> {
            fragment = new HomeProviderFragment();
            loadFragment(fragment);
        });
        binding.RRProfile.setOnClickListener(view -> {

            fragment = new ProfileProviderFragment();
            loadFragment(fragment);

        });

        binding.RRBookNow.setOnClickListener(view -> {

            fragment = new MyBookingFragment();
            loadFragment(fragment);

        });

       fragment = new HomeProviderFragment();
        loadFragment(fragment);
    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;

        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

    /*    fragment = new HomeFragmentOne();
        loadFragment(fragment);*/

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}