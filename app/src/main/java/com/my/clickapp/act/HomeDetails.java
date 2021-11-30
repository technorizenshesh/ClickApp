package com.my.clickapp.act;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.my.clickapp.LikeDisllikeClickListener;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.HomeServicesRecyclerViewAdapter;
import com.my.clickapp.adapter.SelectedServicesAdapter;
import com.my.clickapp.adapter.ShopVideoImgageListRecyclerViewAdapter;
import com.my.clickapp.databinding.ActivityHomeDetailsBinding;
import com.my.clickapp.model.BookingModel;
import com.my.clickapp.model.PaymentModel;
import com.my.clickapp.model.serviceDetailsModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeDetails extends AppCompatActivity implements LikeDisllikeClickListener
{

    ActivityHomeDetailsBinding binding;
    HomeServicesRecyclerViewAdapter mAdapterCategory;
    ShopVideoImgageListRecyclerViewAdapter mAdapterVideolList;
    SelectedServicesAdapter mAdapterSelected;
    private ArrayList<serviceDetailsModel.Result.ServiceDetail> modelListCategory = new ArrayList<>();
    private ArrayList<serviceDetailsModel.Result.ShopVideo> modelListVideoList = new ArrayList<>();
    private ArrayList<PaymentModel.Result> modelListCategorySelectedServices = new ArrayList<>();
    SessionManager sessionManager;

    String VideoLink="";
    ProgressBar progressBar1;
    private View promptsView;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_details);

        sessionManager = new SessionManager(HomeDetails.this);

        binding.RRback.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.RRBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> Serviceslist = new ArrayList<>();

                for (int i = 0; i < modelListCategory.size(); i++)
                {
                    if(modelListCategory.get(i).isSelected())
                    {
                        String services_id = modelListCategory.get(i).getId();

                        Serviceslist.add(services_id);
                    }
                }

                String services_id = TextUtils.join(",",Serviceslist);

                Log.d("services_id_final-- >", services_id);

                if(services_id.equalsIgnoreCase(""))
                {
                    Toast.makeText(HomeDetails.this, "Please Selected Services..", Toast.LENGTH_SHORT).show();

                }else
                {
                    if (sessionManager.isNetworkAvailable()) {

                        binding.progressBar.setVisibility(View.VISIBLE);

                        getShopSummery(services_id);

                    }else {
                        Toast.makeText(HomeDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                }
               // AlertDaliog();

            }
        });

        binding.RRVideoImg.setOnClickListener(v -> {

            startActivity(new Intent(HomeDetails.this, VideoPlayActivity.class).putExtra("link",VideoLink));

        });


        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getShopDetails();

        }else {
            Toast.makeText(HomeDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

    }

    private void setAdapter(ArrayList<serviceDetailsModel.Result.ServiceDetail> modelListCategory) {

        mAdapterCategory = new HomeServicesRecyclerViewAdapter(HomeDetails.this, modelListCategory);
        binding.recyServices.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeDetails.this);
        binding.recyServices.setLayoutManager(linearLayoutManager);
        binding.recyServices.setAdapter(mAdapterCategory);

        mAdapterCategory.SetOnItemClickListener(new HomeServicesRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, serviceDetailsModel.Result.ServiceDetail model) {

            }
        });
    }

    private void setAdapterVideoList(ArrayList<serviceDetailsModel.Result.ShopVideo> modelListVideoList) {

        mAdapterVideolList = new ShopVideoImgageListRecyclerViewAdapter(HomeDetails.this, modelListVideoList,HomeDetails.this);
        binding.recyVideoList.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeDetails.this);
        binding.recyVideoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        binding.recyVideoList.setAdapter(mAdapterVideolList);

        mAdapterVideolList.SetOnItemClickListener(new ShopVideoImgageListRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, serviceDetailsModel.Result.ShopVideo model) {

            }
        });

    }



    private void SelectedSrviceAdapter(ArrayList<PaymentModel.Result> modelListCategorySelectedServices,RecyclerView recyclerView) {

        mAdapterSelected = new SelectedServicesAdapter(HomeDetails.this,modelListCategorySelectedServices);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HomeDetails.this);
       recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mAdapterSelected);

        mAdapterSelected.SetOnItemClickListener(new SelectedServicesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, PaymentModel.Result model) {

            }
        });
    }


    public void getShopDetails(){

        String Shop_id = Preference.get(HomeDetails.this, Preference.KEY_id);
        String User_id = Preference.get(HomeDetails.this, Preference.KEY_USER_ID);

        Call<serviceDetailsModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .shop_details(Shop_id,User_id);
        call.enqueue(new Callback<serviceDetailsModel>() {
            @Override
            public void onResponse(Call<serviceDetailsModel> call, Response<serviceDetailsModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    serviceDetailsModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        VideoLink=myclass.getResult().getVideo().toString();

                        binding.txtShopName.setText(myclass.getResult().getShopName() + "");
                        binding.txtShopDesc.setText(myclass.getResult().getDescription() + "");

                        Preference.save(HomeDetails.this, Preference.KEY_Provider_id,myclass.getResult().getProviderId());
                        Preference.save(HomeDetails.this, Preference.KEY_Shope_name,myclass.getResult().getShopName());

                        if (myclass.getResult().getShopImage() != null) {
                            Picasso.get().load(myclass.getResult().getShopImage()).into(binding.shopImg);
                        }

                        modelListCategory = (ArrayList<serviceDetailsModel.Result.ServiceDetail>) myclass.getResult().getServiceDetails();
                        modelListVideoList = (ArrayList<serviceDetailsModel.Result.ShopVideo>) myclass.getResult().getShopVideo();

                        if(modelListCategory.size()==0)
                        {
                            binding.RRBook.setVisibility(View.GONE);
                            binding.recyServices.setVisibility(View.GONE);
                            binding.txtEmty.setVisibility(View.VISIBLE);

                        }else
                        {
                            binding.RRBook.setVisibility(View.VISIBLE);
                            binding.recyServices.setVisibility(View.VISIBLE);
                            binding.txtEmty.setVisibility(View.GONE);

                            setAdapter(modelListCategory);
                        }

                        if(modelListVideoList!=null)
                        {
                            setAdapterVideoList(modelListVideoList);
                        }





                    } else
                        {
                        Toast.makeText(HomeDetails.this, message, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<serviceDetailsModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getShopSummery(String Services_id) {

        Call<PaymentModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_service_price(Services_id);
        call.enqueue(new Callback<PaymentModel>() {
            @Override
            public void onResponse(Call<PaymentModel> call, Response<PaymentModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    PaymentModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                     modelListCategorySelectedServices = (ArrayList<PaymentModel.Result>) myclass.getResult();

                     if(modelListCategorySelectedServices!=null)
                     {
                         AlertDaliog(modelListCategorySelectedServices,myclass.getTotalPrice(),myclass.getProviderId(),Services_id);
                     }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<PaymentModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(HomeDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void AlertDaliog(ArrayList<PaymentModel.Result> modelListCategorySelectedServices,String Price,String ProviderId,String Services_id) {

        LayoutInflater li;
        TextView txtExit;
        TextView txtPrice;
        TextView txtCofirm;

        RecyclerView recySelectedServices;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(HomeDetails.this);
        promptsView = li.inflate(R.layout.alert_dailoge_payment, null);
        txtExit = (TextView) promptsView.findViewById(R.id.txtExit);
        txtPrice = (TextView) promptsView.findViewById(R.id.txtPrice);
        txtCofirm = (TextView) promptsView.findViewById(R.id.txtCofirm);
        progressBar1 = (ProgressBar) promptsView.findViewById(R.id.progressBar1);
        recySelectedServices = (RecyclerView) promptsView.findViewById(R.id.recySelectedServices);
        alertDialogBuilder = new AlertDialog.Builder(HomeDetails.this);
        alertDialogBuilder.setView(promptsView);

        SelectedSrviceAdapter(modelListCategorySelectedServices,recySelectedServices);

        txtPrice.setText("$"+Price);

        txtExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

            }
        });

        txtCofirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sessionManager.isNetworkAvailable()) {

                    progressBar1.setVisibility(View.VISIBLE);

                    getUserBooking(ProviderId,Services_id,Price);

                }else {
                    Toast.makeText(HomeDetails.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                }

                alertDialog.dismiss();
            }
        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void getUserBooking(String ProviderId,String ServicesId,String Price) {

        String User_id =Preference.get(HomeDetails.this,Preference.KEY_USER_ID);

        Call<BookingModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .user_booking(User_id,ProviderId,ServicesId,Price);
        call.enqueue(new Callback<BookingModel>() {
            @Override
            public void onResponse(Call<BookingModel> call, Response<BookingModel> response) {
                try {

                    progressBar1.setVisibility(View.GONE);

                    BookingModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        Toast.makeText(HomeDetails.this, ""+myclass.getMessage(), Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(HomeDetails.this,ReviewActivity.class));
                        finish();
                    }
                } catch (Exception e) {
                    progressBar1.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<BookingModel> call, Throwable t) {
                progressBar1.setVisibility(View.GONE);
                Toast.makeText(HomeDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void getLikeDisLike(String video_id){

        String User_id = Preference.get(HomeDetails.this, Preference.KEY_USER_ID);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_like_provider_video(User_id,video_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String responseString = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseString);

                    String Result=jsonObject.getString("result");

                    getShopDetails();

                    Toast.makeText(HomeDetails.this, ""+Result, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(HomeDetails.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }

    @Override
    public void onItemClick(String Video_id) {

        getLikeDisLike(Video_id);
    }
}