package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.widget.Toast;

import com.my.clickapp.MainActivity;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityForogotPasswordBinding;

public class ForogotPassword extends AppCompatActivity {

    ActivityForogotPasswordBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_forogot_password);

        binding.llCreate.setOnClickListener(v -> {

            Toast.makeText(this, "Please Check Your Mail.", Toast.LENGTH_SHORT).show();
            finish();

        });

        binding.btLogin.setOnClickListener(v -> {

            startActivity(new Intent(ForogotPassword.this, LoginActivity.class));
            finish();
        });

    }
}