package com.my.clickapp.act.actProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.act.CreateProfile;
import com.my.clickapp.act.HomeActivity;
import com.my.clickapp.act.LoginActivity;
import com.my.clickapp.databinding.ActivityAddvideoBinding;
import com.my.clickapp.model.LoginModel;
import com.my.clickapp.model.VideoUpload;
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

import static android.os.Environment.getExternalStoragePublicDirectory;

public class Addvideo extends AppCompatActivity {

    ActivityAddvideoBinding binding;

    private static int RESULT_LOAD_VIDEO = 1;
    private static int RESULT_LOAD_image = 2;

    String decodableString;
    MediaController mediaController;

    private AlertDialog  alertDialog;

    String type ="";

    private File UserProfile_img_video;
    private File destinationDirectory;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addvideo);

        sessionManager = new SessionManager(Addvideo.this);


        binding.RRBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.lleditProfile.setOnClickListener(view -> {

            AlertDaliog();

        });

        binding.lleditSave.setOnClickListener(view -> {

            if(!type.equalsIgnoreCase("") && UserProfile_img_video!=null)
            {

                String title=binding.edtTitle.getText().toString();

                if(title.equalsIgnoreCase(""))
                {
                    Toast.makeText(this, "Please Add Title...", Toast.LENGTH_SHORT).show();

                }else
                {

                    int duration = binding.videoView.getDuration();

                    String durationTIme = millisecondsToTime(duration);

                 if (sessionManager.isNetworkAvailable()) {

                        binding.progressBar.setVisibility(View.VISIBLE);

                        ApIVideoUpMehod(title,type,durationTIme);

                    }else {
                        Toast.makeText(this, R.string.checkInternet, Toast.LENGTH_SHORT).show();
                    }
                }

            }else
            {

                Toast.makeText(this, "Please Add Video and image...", Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void setUp(String videoUrl) {
        // Uri object to refer the
        // resource from the videoUrl
        Uri uri = Uri.parse(videoUrl);
        // sets the resource from the
        // videoUrl to the videoView
        binding.videoView.setVideoURI(uri);

        mediaController = new MediaController(this);

        mediaController.setAnchorView(binding.videoView);
        // sets the media player to the videoView
        mediaController.setMediaPlayer(binding.videoView);
        // sets the media controller to the videoView
        binding.videoView.setMediaController(mediaController);
        // starts the video
        binding.videoView.start();

        mediaController.getDrawingTime();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When a video is picked
            if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK
                    && null != data) {

                binding.imgDemo.setVisibility(View.GONE);
                binding.wrapper.setVisibility(View.VISIBLE);

                // Get the video from data
                Uri selectedVideo = data.getData();
                String[] filePathColumn = {MediaStore.Video.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedVideo,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                decodableString = cursor.getString(columnIndex);
                Log.i("mok", decodableString);
                cursor.close();

                UserProfile_img_video = FileUtil.from(this, selectedVideo);

                type="Video";

                setUp(decodableString);

                // upload(decodableString);

            }else if (requestCode == RESULT_LOAD_image && resultCode == RESULT_OK
                    && null != data) {

                binding.imgDemo.setVisibility(View.VISIBLE);
                binding.wrapper.setVisibility(View.GONE);
               // Get the video from data
                Uri selectedImage = data.getData();

                try {
                 Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                  UserProfile_img_video = FileUtil.from(this, selectedImage);
                    binding.imgDemo.setImageBitmap(bitmap);
                   // Glide.with(this).load(bitmap).circleCrop().into(binding.imgDemo);

                    type="Image";

                } catch (IOException e) {
                    e.printStackTrace();
                }

                // upload(decodableString);


            } else {

                Toast.makeText(this, "You haven't picked any video/Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }


    private void AlertDaliog() {

        LayoutInflater li;
        RelativeLayout RRimage;
        RelativeLayout RRVideo;
        AlertDialog.Builder alertDialogBuilder;
        li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.alert_dailoge_new, null);
        RRimage = (RelativeLayout) promptsView.findViewById(R.id.RRimage);
        RRVideo = (RelativeLayout) promptsView.findViewById(R.id.RRVideo);
        alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(promptsView);

        RRimage.setOnClickListener(v -> {

            Dexter.withActivity(Addvideo.this)
                    .withPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {

                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, RESULT_LOAD_image);

                            } else {
                                showSettingDialogue();
                            }
                        }
                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();


            alertDialog.dismiss();

        });

        RRVideo.setOnClickListener(v -> {

            Dexter.withActivity(Addvideo.this)
                    .withPermissions(Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport report) {
                            if (report.areAllPermissionsGranted()) {

                                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                        android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(galleryIntent, RESULT_LOAD_VIDEO);

                            } else {
                                showSettingDialogue();
                            }
                        }
                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                    }).check();



            alertDialog.dismiss();

        });

        alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void showSettingDialogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Addvideo.this);
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


    private void ApIVideoUpMehod(String Title,String Type,String durationTIme) {

       //String Provider_id = "1";
       String Provider_id =  Preference.get(Addvideo.this,Preference.KEY_USER_ID);

        MultipartBody.Part imgFile = null;

        if (UserProfile_img_video == null) {

        }else {
            RequestBody requestFileOne = RequestBody.create(MediaType.parse("image/*"), UserProfile_img_video);
            imgFile = MultipartBody.Part.createFormData("video", UserProfile_img_video.getName(), requestFileOne);
        }

        RequestBody provider_id = RequestBody.create(MediaType.parse("text/plain"), Provider_id);
        RequestBody title = RequestBody.create(MediaType.parse("text/plain"), Title);
        RequestBody type = RequestBody.create(MediaType.parse("text/plain"), Type);
        RequestBody DurationTIme = RequestBody.create(MediaType.parse("text/plain"), durationTIme);

        Call<VideoUpload> call = RetrofitClients
                .getInstance()
                .getApi()
                .add_provider_video(provider_id,title,type,DurationTIme,imgFile);

        call.enqueue(new Callback<VideoUpload>() {
            @Override
            public void onResponse(Call<VideoUpload> call, Response<VideoUpload> response) {

                binding.progressBar.setVisibility(View.GONE);

                VideoUpload finallyPr = response.body();

                String status = finallyPr.status;

                if (status.equalsIgnoreCase("1")) {

                    Toast.makeText(Addvideo.this, "" + finallyPr.message, Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(Addvideo.this,MyTimeLine.class));

                } else {
                    binding.progressBar.setVisibility(View.GONE);
                    Toast.makeText(Addvideo.this, finallyPr.message, Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<VideoUpload> call, Throwable t) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(Addvideo.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private String millisecondsToTime(long milliseconds) {
        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String secondsStr = Long.toString(seconds);
        String secs;
        if (secondsStr.length() >= 2) {
            secs = secondsStr.substring(0, 2);
        } else {
            secs = "0" + secondsStr;
        }

        return minutes + ":" + secs;
    }



}