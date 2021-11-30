package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.actProvider.WriteReviewListProvider;
import com.my.clickapp.adapter.WriteReviewAdapter;
import com.my.clickapp.databinding.ActivityReportBinding;
import com.my.clickapp.databinding.ActivityWriteReviewListBinding;
import com.my.clickapp.model.GetRewiewList;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    ActivityReportBinding binding;
    WriteReviewAdapter mAdapter;

    ArrayList<GetRewiewList.Result> modelList=new ArrayList<>();
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_report);

        binding.RRBack.setOnClickListener(v -> {

            onBackPressed();

        });
        binding.WriteReview.setOnClickListener(v -> {

            // startActivity(new Intent(WriteReviewList.this, ReviewActivity.class));

        });

        sessionManager = new SessionManager(ReportActivity.this);

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getReviewAll();

        }else {

            Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


    }

    private void setAdapter(ArrayList<GetRewiewList.Result> modelList) {

        mAdapter = new WriteReviewAdapter(ReportActivity.this,modelList);
        binding.recyReport.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ReportActivity.this);
        binding.recyReport.setLayoutManager(linearLayoutManager);
        binding.recyReport.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new WriteReviewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, GetRewiewList.Result model) {

            }
        });
    }

    public void getReviewAll() {

        String Provider_id = Preference.get(ReportActivity.this,Preference.KEY_Provider_id);

        Call<GetRewiewList> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_shop_review(Provider_id);
        call.enqueue(new Callback<GetRewiewList>() {
            @Override
            public void onResponse(Call<GetRewiewList> call, Response<GetRewiewList> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    GetRewiewList myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {
                        binding.txtEmty.setVisibility(View.GONE);
                        modelList= (ArrayList<GetRewiewList.Result>) myclass.getResult();

                        setAdapter(modelList);

                    } else {
                        binding.txtEmty.setVisibility(View.VISIBLE);

                    }
                } catch (Exception e) {
                    binding.txtEmty.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<GetRewiewList> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                binding.txtEmty.setVisibility(View.VISIBLE);
            }
        });
    }

}