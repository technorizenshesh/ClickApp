package com.my.clickapp.act;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.HomeSaloonRecyclerViewAdapter;
import com.my.clickapp.adapter.SeeAllSaloonRecyclerViewAdapter;
import com.my.clickapp.databinding.ActivitySeeAllShoplistBinding;
import com.my.clickapp.model.CourseModal;
import com.my.clickapp.model.NearestShopModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SeeAllShoplist extends AppCompatActivity {

    ActivitySeeAllShoplistBinding binding;
    SeeAllSaloonRecyclerViewAdapter mAdapter;
    private ArrayList<NearestShopModel.Result> modelList = new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_all_shoplist);

        sessionManager = new SessionManager(SeeAllShoplist.this);

       /* if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getNearesShop();

        }else {
            Toast.makeText(SeeAllShoplist.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
*/
        binding.RRback.setOnClickListener(v -> {
            onBackPressed();
        });


    }

    private void setAdapter(ArrayList<NearestShopModel.Result> modelList) {

        mAdapter = new SeeAllSaloonRecyclerViewAdapter(SeeAllShoplist.this, modelList);
        binding.recycleSeeAll.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SeeAllShoplist.this);
        binding.recycleSeeAll.setLayoutManager(linearLayoutManager);
        binding.recycleSeeAll.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new SeeAllSaloonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, NearestShopModel.Result model) {

                Preference.save(SeeAllShoplist.this, Preference.KEY_id, model.id);

                startActivity(new Intent(SeeAllShoplist.this, HomeDetails.class));

            }
        });
    }



}