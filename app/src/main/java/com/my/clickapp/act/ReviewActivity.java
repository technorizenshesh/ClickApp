package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityReviewBinding;

public class ReviewActivity extends AppCompatActivity {

    ActivityReviewBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_review);

        binding.RRBack.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}