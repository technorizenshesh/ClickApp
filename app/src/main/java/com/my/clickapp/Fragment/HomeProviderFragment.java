package com.my.clickapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bumptech.glide.Glide;
import com.my.clickapp.AcceptClickListener;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.LoginActivity;
import com.my.clickapp.act.MyBookingUser;
import com.my.clickapp.act.actProvider.Addservices;
import com.my.clickapp.act.actProvider.HomeActivityProvider;
import com.my.clickapp.adapter.HomeRecyclerViewAdapter;
import com.my.clickapp.databinding.FragmentHomeBinding;
import com.my.clickapp.databinding.FragmentHomeProviderBinding;
import com.my.clickapp.model.AcceptedRejectModel;
import com.my.clickapp.model.AllOrdermOdel;
import com.my.clickapp.model.HomeModel;
import com.my.clickapp.model.MyUserBookingModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeProviderFragment extends Fragment implements AcceptClickListener {


    FragmentHomeProviderBinding binding;

    HomeRecyclerViewAdapter mAdapter;
    private ArrayList<AllOrdermOdel.Result> modelList = new ArrayList<>();
    private SessionManager sessionManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_provider, container, false);

        String UserName = Preference.get(getActivity(),Preference.KEY_User_name);
        String UserImg = Preference.get(getActivity(),Preference.KEY_USer_img);

        if(!UserImg.equalsIgnoreCase(""))
        {
            Glide.with(this).load(UserImg).into(binding.imgUser);
        }

        binding.txtName.setText(UserName);

        binding.txtAddServices.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), Addservices.class));

        });

        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getMyProviderBooking("Pending");

        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();

    }

    private void setAdapter(ArrayList<AllOrdermOdel.Result> modelList) {

     /*   modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));
*/
        mAdapter = new HomeRecyclerViewAdapter(getActivity(),modelList, HomeProviderFragment.this);
        binding.recyclerReequuest.setHasFixedSize(true);
        binding.recyclerReequuest.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerReequuest.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new HomeRecyclerViewAdapter.OnItemClickListener() {
            public void onItemClick(View view, int position, AllOrdermOdel.Result model) {

            }
        });
    }


    public void getMyProviderBooking(String status) {

        String Provider_id = Preference.get(getActivity(),Preference.KEY_USER_ID);

        Call<AllOrdermOdel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_provider_booking(Provider_id,status);
        call.enqueue(new Callback<AllOrdermOdel>() {
            @Override
            public void onResponse(Call<AllOrdermOdel> call, Response<AllOrdermOdel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    AllOrdermOdel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList= (ArrayList<AllOrdermOdel.Result>) myclass.result;

                        setAdapter(modelList);
                    }
                } catch (Exception e) {
                    binding.progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AllOrdermOdel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void accepte_reject_booking(String BookingId,String status) {

        Call<AcceptedRejectModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .accepte_reject_booking(BookingId,status);
        call.enqueue(new Callback<AcceptedRejectModel>() {
            @Override
            public void onResponse(Call<AcceptedRejectModel> call, Response<AcceptedRejectModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    AcceptedRejectModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        getMyProviderBooking("Pending");

                        Toast.makeText(getActivity(), myclass.getResult().getStatus(), Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    binding.progressBar.setVisibility(View.GONE);
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<AcceptedRejectModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onItemClick(String id, String status){

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            accepte_reject_booking(id,status);

        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }
    }
}