package com.my.clickapp.act.actProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.database.DatabaseUtils;
import android.os.Bundle;

import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityAddvideoBinding;

public class Addvideo extends AppCompatActivity {

    ActivityAddvideoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_addvideo);


        binding.RRBack.setOnClickListener(view -> {
            onBackPressed();
        });

    }
}