package com.my.clickapp.Fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.my.clickapp.GPSTracker;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.HomeActivity;
import com.my.clickapp.act.HomeDetails;
import com.my.clickapp.act.LoginActivity;
import com.my.clickapp.act.MemberShipActivity;
import com.my.clickapp.act.SeeAllShoplist;
import com.my.clickapp.act.ShopAnimationActivityShop;
import com.my.clickapp.adapter.HomeCategoryRecyclerViewAdapter;
import com.my.clickapp.adapter.HomeSaloonRecyclerViewAdapter;
import com.my.clickapp.databinding.FragmentHomeBinding;
import com.my.clickapp.databinding.FragmentHomeOneBinding;
import com.my.clickapp.model.CategoryModel;
import com.my.clickapp.model.HomeModel;
import com.my.clickapp.model.NearestShopModel;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentOne extends Fragment {

    private Fragment fragment;
    FragmentHomeOneBinding binding;

    HomeSaloonRecyclerViewAdapter mAdapter;
    HomeCategoryRecyclerViewAdapter mAdapterCategory;
    private ArrayList<NearestShopModel.Result> modelList = new ArrayList<>();
    private ArrayList<CategoryModel.Result> modelListCategory = new ArrayList<>();
    private SessionManager sessionManager;
    private GPSTracker gpsTracker;

    private double latitude;
    private double longitude;

    String addressStreet = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_one, container, false);

        sessionManager = new SessionManager(getActivity());

        //Gps Lat Long
        gpsTracker = new GPSTracker(getActivity());

        if (gpsTracker.canGetLocation()) {

            latitude = gpsTracker.getLatitude();
            longitude = gpsTracker.getLongitude();

            Log.e("latitude1====", latitude+"");
            Log.e("longitute1====", longitude+"");

        } else {

            gpsTracker.showSettingsAlert();
        }


        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getCategory();

        }else {
            Toast.makeText(getActivity(), R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }

        binding.RRSeeAll.setOnClickListener(v -> {

            startActivity(new Intent(getActivity(), ShopAnimationActivityShop.class));

        });

        getCurrentLocation();

        return binding.getRoot();
    }

    private void setAdapter(ArrayList<NearestShopModel.Result> modelList) {

        mAdapter = new HomeSaloonRecyclerViewAdapter(getActivity(),modelList);
        binding.recyclernearme.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclernearme.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        binding.recyclernearme.setAdapter(mAdapter);

        mAdapter.SetOnItemClickListener(new HomeSaloonRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, NearestShopModel.Result model) {

                Preference.save(getActivity(),Preference.KEY_id,model.id);

                startActivity(new Intent(getActivity(),HomeDetails.class));

            }
        });
    }

    private void setAdapterOne(ArrayList<CategoryModel.Result> modelListCategory) {

        mAdapterCategory = new HomeCategoryRecyclerViewAdapter(getActivity(),modelListCategory,HomeFragmentOne.this);
        binding.recyclerCategory.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        binding.recyclerCategory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, true));
        binding.recyclerCategory.setAdapter(mAdapterCategory);

        mAdapterCategory.SetOnItemClickListener(new HomeCategoryRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, CategoryModel.Result model) {

                Preference.save(getActivity(),Preference.KEY_category_id,model.getId());

                startActivity(new Intent(getActivity(), ShopAnimationActivityShop.class));


            }
        });

    }

    public void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_homeContainer, fragment);
        transaction.addToBackStack("home");
        transaction.commit();
    }


    public void getCurrentLocation() {
        String loc = "";
        if (gpsTracker.canGetLocation()) {
            loc = getAddress(getActivity(), gpsTracker.getLatitude(), gpsTracker.getLongitude());
        }
        Log.e("Location=====", loc);
    }

    public String getAddress(Context context, double latitude, double longitute) {

        Log.e("latitude1====", latitude+"");
        Log.e("longitute1====", longitute+"");

        List<Address> addresses;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude, longitute, 4); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            addressStreet = addresses.get(0).getAddressLine(0);
            // address2 = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            //  city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String region = addresses.get(0).getAdminArea();

            Preference.save(getActivity(), Preference.KEY_address, addressStreet);

            binding.txtAddress.setText(addressStreet + "");

            Log.e("region====", region);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressStreet;
    }

    public void getCategory() {

        Call<CategoryModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .Api_category_list();
        call.enqueue(new Callback<CategoryModel>() {
            @Override
            public void onResponse(Call<CategoryModel> call, Response<CategoryModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    CategoryModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelListCategory = (ArrayList<CategoryModel.Result>) myclass.getResult();

                        setAdapterOne(modelListCategory);

                        getNearesShop();

                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getNearesShop() {

        String User_id = Preference.get(getActivity(),Preference.KEY_USER_ID);

        String lat= String.valueOf(latitude);
        String lon= String.valueOf(longitude);


        Call<NearestShopModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .get_nearest_shop(User_id,lat,lon);
        call.enqueue(new Callback<NearestShopModel>() {
            @Override
            public void onResponse(Call<NearestShopModel> call, Response<NearestShopModel> response) {
                try {

                    binding.progressBar.setVisibility(View.GONE);

                    NearestShopModel myclass = response.body();

                    String status = String.valueOf(myclass.getStatus());
                    String message = myclass.getMessage();

                    if (status.equalsIgnoreCase("1")) {

                        modelList = (ArrayList<NearestShopModel.Result>) myclass.getResult();

                        setAdapter(modelList);


                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<NearestShopModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}