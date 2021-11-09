package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityMemberShipBinding;

public class MemberShipActivity extends AppCompatActivity {

    ActivityMemberShipBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_member_ship);

        binding.RRback.setOnClickListener(v -> {

            onBackPressed();

        });

        binding.RRBook.setOnClickListener(v -> {

            startActivity(new Intent(MemberShipActivity.this, PaymentActivity.class));

        });

        binding.RRSkip.setOnClickListener(v -> {

            startActivity(new Intent(MemberShipActivity.this, HomeActivity.class));

        });
    }
}