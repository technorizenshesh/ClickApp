package com.my.clickapp.act.actProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.firebase.iid.FirebaseInstanceId;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.adapter.CategorySpinnerAdapter;
import com.my.clickapp.databinding.ActivityAddmassageDetailBinding;
import com.my.clickapp.model.CategoryModel;
import com.my.clickapp.model.LoginModel;
import com.my.clickapp.utils.FileUtil;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.my.clickapp.act.CreateProfile.UserProfile_img;


public class AddmassageDetail extends AppCompatActivity {

    ActivityAddmassageDetailBinding binding;

    private Bitmap bitmap;
    private Uri resultUri;
    //private SessionManager sessionManager;
    public static File Shop_img_UserProfile_img1, codmpressedImage1, compressActualFile;
    boolean isProfileImage = false;

    private SessionManager sessionManager;

    String Name = "";
    String Email = "";
    String Mobile = "";
    String Password = "";

    private String ShopName = "";
    private String Location = "";
    private String Description = "";

    int AUTOCOMPLETE_REQUEST_CODE_ADDRESS1 = 102;

    String address = "";
    String Shoplat = "";
    String Shoplong = "";
    String token = "";

    private ArrayList<CategoryModel.Result> modelListCategory = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addmassage_detail);

        sessionManager = new SessionManager(AddmassageDetail.this);

       Intent inten=getIntent();

        if(inten !=null)
        {
             Name=inten.getStringExtra("Name".toString());
             Email=inten.getStringExtra("Email".toString());
             Mobile=inten.getStringExtra("Mobile".toString());
             Password=inten.getStringExtra("Password".toString());
        }

        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(runnable -> {
            token = runnable.getToken();
            Log.e("Tokennnn", token);
        });

        if (!Places.isInitialized()) {
            Places.initialize(AddmassageDetail.this, getString(R.string.place_api_key));
        }

        binding.RRDropUpAddress.setOnClickListener(v -> {

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
            // Start the autocomplete intent.
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(AddmassageDetail.this);

            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE_ADDRESS1);

        });


        binding.RRADDImg.setOnClickListener(v -> {

            Dexter.withActivity(AddmassageDetail.this)
                    .withPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(AddmassageDetail.this);
                                startActivityForResult(intent, 1);
                            } else {
                                showSettingDialogue();
                            }
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();

        });

        binding.llenext.setOnClickListener(v -> {
            Validation();
        });

        if (sessionManager.isNetworkAvailable()) {

            binding.progressBar.setVisibility(View.VISIBLE);

            getCategory();

        } else {
            Toast.makeText(AddmassageDetail.this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
        }


    }


    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddmassageDetail.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                openSetting();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    private void openSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", this.getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        CropImage.ActivityResult result = CropImage.getActivityResult(data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                try {

                    binding.CardEquimentImg.setVisibility(View.VISIBLE);
                    binding.RRAddEuimentImg.setVisibility(View.GONE);

                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                    Shop_img_UserProfile_img1 = FileUtil.from(this, resultUri);

                    Glide.with(this).load(bitmap).into(binding.imgeUSer);

                    isProfileImage = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    codmpressedImage1 = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(Shop_img_UserProfile_img1);
                    Log.e("ActivityTag", "imageFilePAth: " + codmpressedImage1);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }

        } else if (requestCode == AUTOCOMPLETE_REQUEST_CODE_ADDRESS1) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                try {
                    Log.e("addressStreet====", place.getAddress());
                    address = place.getAddress();
                    Shoplat = String.valueOf(place.getLatLng().latitude);
                    Shoplong = String.valueOf(place.getLatLng().longitude);

                    binding.tvPickUp.setText(place.getAddress());


                } catch (Exception e) {
                    e.printStackTrace();
                    //setMarker(latLng);
                }

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
            }

        }
    }


    private void Validation() {

        ShopName = binding.edtShopName.getText().toString();
        Location = binding.tvPickUp.getText().toString();
        Description = binding.edtDescription.getText().toString();

        if (!isProfileImage) {
            Toast.makeText(this, "Please Select Your Image", Toast.LENGTH_SHORT).show();

        } else if (ShopName.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();

        } else if (Location.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

        } else if (Description.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please Enter Mobile", Toast.LENGTH_SHORT).show();

        } else {
            if (sessionManager.isNetworkAvailable()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                ApISignUpMehod();

            } else {
                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void ApISignUpMehod() {

        MultipartBody.Part imgFile = null;
        MultipartBody.Part imgFileShope = null;

        if (Shop_img_UserProfile_img1 == null) {

        } else if (UserProfile_img == null) {

        } else {

            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), Shop_img_UserProfile_img1);
            imgFile = MultipartBody.Part.createFormData("image", Shop_img_UserProfile_img1.getName(), requestFileOne);

            RequestBody requesEUiment = RequestBody.create(MediaType.parse("image/*"), UserProfile_img);
            imgFileShope = MultipartBody.Part.createFormData("shop_image", UserProfile_img.getName(), requesEUiment);

        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), Name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), Email);
        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), Mobile);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), Password);
        RequestBody Token = RequestBody.create(MediaType.parse("text/plain"), token);
        RequestBody lat1 = RequestBody.create(MediaType.parse("text/plain"), Shoplat);
        RequestBody lon1 = RequestBody.create(MediaType.parse("text/plain"), Shoplong);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), "Provider");
        RequestBody shop_name = RequestBody.create(MediaType.parse("text/plain"), ShopName);
        RequestBody shop_address = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), Description);
        RequestBody shop_lat = RequestBody.create(MediaType.parse("text/plain"), Shoplat);
        RequestBody shop_lon = RequestBody.create(MediaType.parse("text/plain"), Shoplong);


        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .provider_signup(name, email, mobile, password, Token, lat1, lon1, type, imgFile, imgFileShope, shop_name, shop_address, shop_lat, shop_lon, description);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                LoginModel finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(AddmassageDetail.this, "" + finallyPr.message, Toast.LENGTH_SHORT).show();


                    Preference.save(AddmassageDetail.this, Preference.KEY_User_name, finallyPr.result.name);
                    Preference.save(AddmassageDetail.this, Preference.KEY_User_email, finallyPr.result.email);
                    Preference.save(AddmassageDetail.this, Preference.KEY_mobile, finallyPr.result.mobile);
                    Preference.save(AddmassageDetail.this, Preference.KEY_USer_img, finallyPr.result.image);

                    startActivity(new Intent(AddmassageDetail.this, HomeActivityProvider.class));
                    finish();

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(AddmassageDetail.this, finallyPr.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(AddmassageDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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

                        CategorySpinnerAdapter customAdapter = new CategorySpinnerAdapter(AddmassageDetail.this, modelListCategory);

                        binding.spinnerCatgory.setAdapter(customAdapter);


                    } else {
                        Toast.makeText(AddmassageDetail.this, message, Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<CategoryModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(AddmassageDetail.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}