package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.my.clickapp.MainActivity;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityChooseAdminUserBinding;

public class ChooseAdminUser extends AppCompatActivity {

    ActivityChooseAdminUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_choose_admin_user);

        binding.btnUser.setOnClickListener(view -> {

            Intent intent=new Intent(ChooseAdminUser.this,LoginActivity.class);
             intent.putExtra("LoginType","User");
            startActivity(intent);
        });

        binding.btnProvider.setOnClickListener(view -> {
            Intent intent=new Intent(ChooseAdminUser.this,LoginActivity.class);
            intent.putExtra("LoginType","Admin");
            startActivity(intent);

        });

    }
}