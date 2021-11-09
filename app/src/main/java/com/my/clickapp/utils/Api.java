package com.my.clickapp.utils;

import com.my.clickapp.model.CategoryModel;
import com.my.clickapp.model.LoginModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {

    //Oops Manager
    String login ="login";
    String user_signup ="signup";
    String social_login ="social_login";
    String Api_category_list ="category_list";
    String get_profile ="get_profile";

    @Multipart
    @POST(user_signup)
    Call<LoginModel>user_signup(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("password") RequestBody password,
            @Part("register_id") RequestBody register_id,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part("type") RequestBody type,
            @Part MultipartBody.Part part1
    );

    @FormUrlEncoded
    @POST(login)
    Call<LoginModel> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("register_id") String register_id
    );


    @FormUrlEncoded
    @POST(social_login)
    Call<LoginModel>social_login(
            @Field("name") String name,
            @Field("email") String email,
            @Field("mobile") String mobile,
            @Field("register_id") String register_id,
            @Field("lat") String lat,
            @Field("lon") String lon,
            @Field("type") String type,
            @Field("social_id") String social_id,
            @Field("image") String image
    );

    @GET(Api_category_list)
    Call<CategoryModel> Api_category_list();

    @FormUrlEncoded
    @POST(get_profile)
    Call<LoginModel> get_profile(
            @Field("user_id") String user_id
    );

}
