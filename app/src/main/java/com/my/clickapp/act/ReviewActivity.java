package com.my.clickapp.act;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityReviewBinding;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity {

    ActivityReviewBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_review);

       String ShopName =  Preference.get(ReviewActivity.this, Preference.KEY_Shope_name);

       binding.txtName.setText(ShopName);

        sessionManager = new SessionManager(ReviewActivity.this);

        binding.RRBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.lleditProfile.setOnClickListener(view -> {

            String comment=binding.etComment.getText().toString();
            float ratingValue =  binding.rating.getRating();

            String rat= String.valueOf(ratingValue);

            if(rat.equalsIgnoreCase("0.0"))
            {
                Toast.makeText(this, "Please Add Rat.....", Toast.LENGTH_SHORT).show();

            }else {

                if (sessionManager.isNetworkAvailable()) {

                    binding.progressBar.setVisibility(View.VISIBLE);

                    AddRatingMethos(comment,rat);

                }else {

                    Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void AddRatingMethos(String comment,String rat){

        String user_id = Preference.get(ReviewActivity.this, Preference.KEY_USER_ID);
        String Provider_id = Preference.get(ReviewActivity.this, Preference.KEY_Provider_id);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_rating(user_id,Provider_id,comment,rat);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    String responseString = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseString);

                    String message=jsonObject.getString("message");

                    //   Toast.makeText(VideoPlayActivity.this, ""+Result, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(new Intent(ReviewActivity.this,HomeActivity.class)));
                                          finish();

                } catch (Exception e) {
                    binding.progressBar.setVisibility(View.GONE);
                    e.printStackTrace();

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                binding.progressBar.setVisibility(View.GONE);

                Toast.makeText(ReviewActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}