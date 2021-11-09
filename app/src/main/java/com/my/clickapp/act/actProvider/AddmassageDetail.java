package com.my.clickapp.act.actProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityAddmassageDetailBinding;


public class AddmassageDetail extends AppCompatActivity {

    ActivityAddmassageDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_addmassage_detail);

    binding.llenext.setOnClickListener(v -> {

       startActivity(new Intent(AddmassageDetail.this, Addservices.class));

    });

    }
}