package com.my.clickapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.MyBookingRecyclerViewAdapter;
import com.my.clickapp.databinding.FragmentMybookingBinding;
import com.my.clickapp.model.AllOrdermOdel;
import com.my.clickapp.model.HomeModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyBookingFragment extends Fragment {


    FragmentMybookingBinding binding;

    MyBookingRecyclerViewAdapter mAdapter;
    private ArrayList<AllOrdermOdel.Result> modelList = new ArrayList<>();

    SessionManager sessionManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_mybooking, container, false);

      //  setAdapter();

        binding.RRBack.setOnClickListener(v -> {
            getActivity().onBackPressed();
        });

        sessionManager = new SessionManager(getActivity());

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getMyProviderBooking("Accepted");

        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        return binding.getRoot();

    }

    private void setAdapter(ArrayList<AllOrdermOdel.Result> modelList) {

     /*   modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));
        modelList.add(new HomeModel("Chelsea teams"));*/

        mAdapter = new MyBookingRecyclerViewAdapter(getActivity(),modelList);
        binding.recyclerBooking.setHasFixedSize(true);
        binding.recyclerBooking.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.recyclerBooking.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new MyBookingRecyclerViewAdapter.OnItemClickListener() {
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


}