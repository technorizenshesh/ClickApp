package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityHomeDetailsBinding;

public class HomeDetails extends AppCompatActivity {

    ActivityHomeDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_home_details);

        binding.RRback.setOnClickListener(view -> {

            onBackPressed();

        });

        binding.RRBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeDetails.this, MemberShipActivity.class));

            }
        });
    }
}