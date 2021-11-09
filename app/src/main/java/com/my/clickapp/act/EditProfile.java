package com.my.clickapp.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityEditProfileBinding;
import com.my.clickapp.model.LoginModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfile extends AppCompatActivity {

    ActivityEditProfileBinding binding;

    String Name="";
    String Email="";
    String Mobile="";
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile);

        binding.RRBack.setOnClickListener(view -> {
            onBackPressed();
        });

         sessionManager = new SessionManager(EditProfile.this);

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getProfileMethod();

        }else {
            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


    }


    private void getProfileMethod(){

        String Userid= Preference.get(EditProfile.this,Preference.KEY_USER_ID);

        Call<LoginModel> call = RetrofitClients.getInstance().getApi()
                .get_profile(Userid);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                LoginModel finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    // Preference.save(LoginActivity.this,Preference.KEY_USER_ID,finallyPr.result.id);

                    Name=finallyPr.result.name.toString();
                    Email=finallyPr.result.email.toString();
                    Mobile=finallyPr.result.mobile.toString();


                    binding.edtName.setText(finallyPr.result.name+"");
                    binding.edtEmail.setText(finallyPr.result.email+"");
                    binding.edtMobile.setText(finallyPr.result.mobile+"");


                    if(!finallyPr.result.image.equalsIgnoreCase(""))
                    {
                        Glide.with(EditProfile.this).load(finallyPr.result.image).into(binding.imgeUSer);
                    }

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(EditProfile.this, finallyPr.message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(EditProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}