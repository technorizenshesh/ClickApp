package com.my.clickapp.act;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.my.clickapp.Preference;
import com.my.clickapp.R;
import com.my.clickapp.databinding.ActivityVideoPlayBinding;
import com.my.clickapp.utils.RetrofitClients;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class VideoPlayActivity extends AppCompatActivity {

    ActivityVideoPlayBinding binding;

    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_video_play);

        Intent intent=getIntent();

        if(intent!=null)
        {
            String ViLink=intent.getStringExtra("link").toString();

            if(!ViLink.equalsIgnoreCase(""))
            {

                setUp(ViLink);
            }else
            {
                finish();
                Toast.makeText(this, "Video not Play..", Toast.LENGTH_SHORT).show();
            }

            Update_count();

        }

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

        binding.progressBar.setVisibility(View.VISIBLE);

        binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {

                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int arg1, int arg2) {
                        // TODO Auto-generated method stub
                        Log.e(TAG, "Changed");
                        binding.progressBar.setVisibility(View.GONE);
                        mp.start();
                    }
                });
            }
        });
    }

    public void Update_count(){

        String User_id = Preference.get(VideoPlayActivity.this, Preference.KEY_USER_ID);
        String Video_id = Preference.get(VideoPlayActivity.this, Preference.KEY_Video_id);

        Call<ResponseBody> call = RetrofitClients
                .getInstance()
                .getApi()
                .update_count(User_id,Video_id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String responseString = response.body().string();

                    JSONObject jsonObject = new JSONObject(responseString);

                    String Result=jsonObject.getString("message");

                 //   Toast.makeText(VideoPlayActivity.this, ""+Result, Toast.LENGTH_SHORT).show();


                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                Toast.makeText(VideoPlayActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
    }
}