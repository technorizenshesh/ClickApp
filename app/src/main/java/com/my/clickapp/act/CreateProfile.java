package com.my.clickapp.act;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

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

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.clickapp.GPSTracker;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.actProvider.HomeActivityProvider;
import com.my.clickapp.databinding.ActivityCreateProfileBinding;
import com.my.clickapp.model.LoginModel;
import com.my.clickapp.utils.FileUtil;
import com.my.clickapp.utils.RetrofitClients;
import com.my.clickapp.utils.SessionManager;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.List;

import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateProfile extends AppCompatActivity {

    ActivityCreateProfileBinding binding;
    String LoginType="";

    private Bitmap bitmap;
    private Uri resultUri;
    //private SessionManager sessionManager;
    public static File UserProfile_img, codmpressedImage, compressActualFile;
    boolean isProfileImage=false;

    String Name="";
    String Email="";
    String Mobile="";
    String Password="";

    private SessionManager sessionManager;
    String token="gjghj";
    String latitude="";
    String longitude="";
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_profile);

        Intent intent=getIntent();

        if(intent !=null)
        {
            LoginType=intent.getStringExtra("LoginTypeSign");
        }

        sessionManager = new SessionManager(CreateProfile.this);

        //Gps Lat Long
        gpsTracker = new GPSTracker(CreateProfile.this);
        if (gpsTracker.canGetLocation()) {

            latitude = String.valueOf(gpsTracker.getLatitude());
            longitude = String.valueOf(gpsTracker.getLongitude());

        } else {
            gpsTracker.showSettingsAlert();
        }

        binding.llLogin.setOnClickListener(v -> {

            startActivity(new Intent(CreateProfile.this, LoginActivity.class));

        });

        binding.btSignUp.setOnClickListener(v -> {

            if(LoginType.equalsIgnoreCase("User"))
            {
                Validation("User");

                startActivity(new Intent(CreateProfile.this, MemberShipActivity.class));

            }else
            {
                startActivity(new Intent(CreateProfile.this, HomeActivityProvider.class));
            }

        });

        binding.RRUserImg.setOnClickListener(v -> {

            Dexter.withActivity(CreateProfile.this)
                    .withPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {
                                Intent intent = CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).getIntent(CreateProfile.this);
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
    }



    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateProfile.this);
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
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                    UserProfile_img = FileUtil.from(this, resultUri);

                    Glide.with(this).load(bitmap).circleCrop().into(binding.imgeUSer);

                    isProfileImage = true;

                } catch (IOException e) {
                    e.printStackTrace();
                }

                try {
                    codmpressedImage = new Compressor(this)
                            .setMaxWidth(640)
                            .setMaxHeight(480)
                            .setQuality(75)
                            .setCompressFormat(Bitmap.CompressFormat.WEBP)
                            .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_PICTURES).getAbsolutePath())
                            .compressToFile(UserProfile_img);
                    Log.e("ActivityTag", "imageFilePAth: " + codmpressedImage);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void Validation(String Type) {

        Name = binding.edtName.getText().toString();
        Email = binding.edtEmail.getText().toString();
        Mobile = binding.edtMobile.getText().toString();
        Password = binding.edtPassword.getText().toString();

        if(!isProfileImage)
        {
            Toast.makeText(this, "Please Select Your Image", Toast.LENGTH_SHORT).show();

        }else if(Name.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Name", Toast.LENGTH_SHORT).show();

        }else if(Email.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Email", Toast.LENGTH_SHORT).show();

        }else if(Mobile.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Mobile", Toast.LENGTH_SHORT).show();

        }else if(Password.equalsIgnoreCase(""))
        {
            Toast.makeText(this, "Please Enter Password", Toast.LENGTH_SHORT).show();

        }else
        {

            if (sessionManager.isNetworkAvailable()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                ApISignUpMehod(Type);

            }else {
                Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void ApISignUpMehod(String Type) {

        MultipartBody.Part imgFile = null;

        if (UserProfile_img == null) {

        } else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), UserProfile_img);
            imgFile = MultipartBody.Part.createFormData("image", UserProfile_img.getName(), requestFileOne);
        }

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), Name);
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), Email);
        RequestBody mobile = RequestBody.create(MediaType.parse("text/plain"), Mobile);
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), Password);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), Type);
        RequestBody lat = RequestBody.create(MediaType.parse("text/plain"), latitude);
        RequestBody lon = RequestBody.create(MediaType.parse("text/plain"), longitude);
        RequestBody Register_id = RequestBody.create(MediaType.parse("text/plain"), token);

        Call<LoginModel> call = RetrofitClients
                .getInstance()
                .getApi()
                .user_signup(name, email, mobile, password, Register_id, lat, lon, type, imgFile);

        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                binding.progressBar.setVisibility(View.GONE);

                LoginModel finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    if(finallyPr.result.type.equalsIgnoreCase("User"))
                    {
                        Toast.makeText(CreateProfile.this, "" + finallyPr.message, Toast.LENGTH_SHORT).show();

                        Preference.save(CreateProfile.this, Preference.KEY_User_name, finallyPr.result.getName());
                        Preference.save(CreateProfile.this, Preference.KEY_User_email, finallyPr.result.email);
                        Preference.save(CreateProfile.this, Preference.KEY_USer_img, finallyPr.result.image);

                        startActivity(new Intent(CreateProfile.this, MemberShipActivity.class));
                        finish();

                    }
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(CreateProfile.this, finallyPr.message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(CreateProfile.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}