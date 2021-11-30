package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.my.clickapp.Fragment.HomeFragment;
import com.my.clickapp.Fragment.HomeFragmentOne;
import com.my.clickapp.Fragment.ProfileFragment;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityHomeBinding;
import com.my.clickapp.databinding.ActivityHomeThreeBinding;

public class HomeActivity extends AppCompatActivity {
    private Fragment fragment;
    ActivityHomeThreeBinding binding;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_home_three);

        binding.RRHome.setOnClickListener(view -> {
           /* fragment = new HomeFragment();
            loadFragment(fragment);*/
            fragment = new HomeFragmentOne();
            loadFragment(fragment);

        });
        binding.RRProfile.setOnClickListener(view -> {

            fragment = new ProfileFragment();
            loadFragment(fragment);

        });

        binding.RRBookNow.setOnClickListener(view -> {

            startActivity(new Intent(HomeActivity.this,ShopAnimationActivityShop.class));
           /* fragment = new HomeFragment();
            loadFragment(fragment);*/

        });

        fragment = new HomeFragmentOne();
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

        fragment = new HomeFragmentOne();
        loadFragment(fragment);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                finishAffinity();
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}