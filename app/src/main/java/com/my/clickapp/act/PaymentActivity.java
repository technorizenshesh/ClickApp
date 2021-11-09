package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityPaymentBinding;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment);

        binding.RRback.setOnClickListener(view -> {

            onBackPressed();

        });
        binding.RRBook.setOnClickListener(view -> {

            startActivity(new Intent(PaymentActivity.this, HomeActivity.class));


        });
    }
}