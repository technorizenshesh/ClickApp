package com.my.clickapp.act.actProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityAddservicesBinding;

public class Addservices extends AppCompatActivity {

    ActivityAddservicesBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_addservices);

        binding.llenext.setOnClickListener(v -> {

            startActivity(new Intent(Addservices.this, HomeActivityProvider.class));
        });

        binding.RRServicesImg.setOnClickListener(v -> {

        });


    }
}