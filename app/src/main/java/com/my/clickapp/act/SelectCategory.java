package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivitySelectCategoryBinding;

public class SelectCategory extends AppCompatActivity {

    ActivitySelectCategoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_select_category);

             binding.btContinue.setOnClickListener(v -> {

                 startActivity(new Intent(SelectCategory.this, MemberShipActivity.class));

             });
    }
}