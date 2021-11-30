package com.my.clickapp.act.actProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.MyTimeLineAdapter;
import com.my.clickapp.databinding.ActivityMyTimeLineBinding;
import com.my.clickapp.model.VideoListModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTimeLine extends AppCompatActivity {

    ActivityMyTimeLineBinding binding;

    MyTimeLineAdapter mAdapter;
    private ArrayList<VideoListModel.Result> modelList = new ArrayList<>();
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_my_time_line);

        binding.RRAdd.setOnClickListener(v -> {

            startActivity(new Intent(MyTimeLine.this,Addvideo.class));
        });

        binding.RRBack.setOnClickListener(v -> {
            onBackPressed();
        });


        sessionManager = new SessionManager(MyTimeLine.this);

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getVideoList();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


    }


    private void setAdapter(ArrayList<VideoListModel.Result> modelList) {

        mAdapter = new MyTimeLineAdapter(MyTimeLine.this, modelList);
        binding.recyVideoList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyTimeLine.this);
        binding.recyVideoList.setLayoutManager(linearLayoutManager);
        binding.recyVideoList.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new MyTimeLineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, VideoListModel.Result model) {

            }
        });

    }

    private void getVideoList(){

       String Provider_id= Preference.get(MyTimeLine.this,Preference.KEY_USER_ID);

        Call<VideoListModel> call = RetrofitClients.getInstance().getApi()
                .get_provider_video(Provider_id);
        call.enqueue(new Callback<VideoListModel>() {
            @Override
            public void onResponse(Call<VideoListModel> call, Response<VideoListModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                VideoListModel finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    modelList= (ArrayList<VideoListModel.Result>) finallyPr.getResult();

                    setAdapter(modelList);

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<VideoListModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

}