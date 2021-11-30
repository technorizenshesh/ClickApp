package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.HomeRecyclerViewAdapter;
import com.my.clickapp.adapter.MyUserBookingAdapter;
import com.my.clickapp.databinding.ActivityMyBookingUserBinding;
import com.my.clickapp.model.HomeModel;
import com.my.clickapp.model.MyUserBookingModel;
import com.my.clickapp.model.PaymentModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingUser extends AppCompatActivity {

    ActivityMyBookingUserBinding binding;
    private MyUserBookingAdapter mAdapter;

    public ArrayList<MyUserBookingModel.Result> modelList=new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_booking_user);

        binding.RRback.setOnClickListener(v -> {

            onBackPressed();

        });

        sessionManager = new SessionManager(MyBookingUser.this);

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getMyUserBooking();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    private void setAdapter(ArrayList<MyUserBookingModel.Result> modelList) {

        mAdapter = new MyUserBookingAdapter(MyBookingUser.this,modelList);
        binding.recyclerReequuest.setHasFixedSize(true);
        binding.recyclerReequuest.setLayoutManager(new LinearLayoutManager(MyBookingUser.this));
        binding.recyclerReequuest.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new MyUserBookingAdapter.OnItemClickListener() {
            public void onItemClick(View view, int position, MyUserBookingModel.Result model) {

            }
        });
    }

    public void getMyUserBooking() {

        String User_id = Preference.get(MyBookingUser.this,Preference.KEY_USER_ID);

        Call<MyUserBookingModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_user_booking(User_id);
        call.enqueue(new Callback<MyUserBookingModel>() {
            @Override
            public void onResponse(Call<MyUserBookingModel> call, Response<MyUserBookingModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);
                    binding.txtEmty.setVisibility(View.GONE);

                    MyUserBookingModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList= (ArrayList<MyUserBookingModel.Result>) myclass.getResult();

                        setAdapter(modelList);

                    }

                } catch (Exception e) {

                    binding.progressBar.setVisibility(View.GONE);
                    binding.txtEmty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<MyUserBookingModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.txtEmty.setVisibility(View.VISIBLE);
                }
        });
    }


}