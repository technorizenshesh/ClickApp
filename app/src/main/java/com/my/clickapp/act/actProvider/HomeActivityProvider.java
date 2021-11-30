package com.my.clickapp.act.actProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.my.clickapp.Fragment.HomeProviderFragment;
import com.my.clickapp.Fragment.MyBookingFragment;
import com.my.clickapp.Fragment.ProfileProviderFragment;
import com.my.clickapp.R;
import com.my.clickapp.act.HomeActivity;
import com.my.clickapp.databinding.ActivityHomeBinding;
import com.my.clickapp.databinding.ActivityHomeProviderBinding;


public class HomeActivityProvider extends AppCompatActivity {

    private Fragment fragment;

    ActivityHomeProviderBinding binding;

    boolean doubleBackToExitPressedOnce = false;
    private long backPressedTime;
    private Toast backToast;

    FragmentManager fragmentManager = getSupportFragmentManager();

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

   /* public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }*/

    public boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            fragmentManager.beginTransaction()
                    .addToBackStack("Home")
                    .replace(R.id.fragment_homeContainer, fragment)//, tag)
                    .commit();
            return true;
        }
        return false;
    }



    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() > 1) {
            fragmentManager.popBackStack();
        } else if (fragmentManager.getBackStackEntryCount() == 1) {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToast.cancel();
                finish();
                return;
            } else {
                backToast = Toast.makeText(HomeActivityProvider.this, "Press once again to exit", Toast.LENGTH_SHORT);
                backToast.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }
}