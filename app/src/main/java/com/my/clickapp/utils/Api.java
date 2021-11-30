package com.my.clickapp.utils;

import com.my.clickapp.model.AcceptedRejectModel;
import com.my.clickapp.model.AllOrdermOdel;
import com.my.clickapp.model.BookingModel;
import com.my.clickapp.model.CategoryModel;
import com.my.clickapp.model.CourseModal;
import com.my.clickapp.model.GetRewiewList;
import com.my.clickapp.model.LoginModel;
import com.my.clickapp.model.MyUserBookingModel;
import com.my.clickapp.model.NearestShopModel;
import com.my.clickapp.model.PaymentModel;
import com.my.clickapp.model.VideoListModel;
import com.my.clickapp.model.VideoUpload;
import com.my.clickapp.model.serviceDetailsModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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
    String update_profile ="update_profile";
    String get_nearest_shop ="get_nearest_shop";
    String shop_details ="shop_details";
    String get_service_price ="get_service_price";
    String get_shop_by_category ="get_shop_by_category";
    String user_booking ="user_booking";
    String get_user_booking ="get_user_booking";
    String get_provider_video ="get_provider_video";
    String add_like_provider_video ="add_like_provider_video";
    String update_count ="update_count";

    String provider_signup ="provider_signup";
    String add_provider_video ="add_provider_video";
    String get_provider_booking ="get_provider_booking";
    String accepte_reject_booking ="accepte_reject_booking";
    String add_rating ="add_rating";
    String get_shop_review ="get_shop_review";

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


    @Multipart
    @POST(add_provider_video)
    Call<VideoUpload>add_provider_video(
            @Part("provider_id") RequestBody provider_id,
            @Part("title") RequestBody title,
            @Part("type") RequestBody type,
            @Part("duration") RequestBody duration,
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

    @FormUrlEncoded
    @POST(get_provider_video)
    Call<VideoListModel> get_provider_video(
            @Field("provider_id") String provider_id
    );

    @FormUrlEncoded
    @POST(get_nearest_shop)
    Call<NearestShopModel> get_nearest_shop(
            @Field("user_id") String user_id,
            @Field("lat") String lat,
            @Field("lon") String lon
    );

    @FormUrlEncoded
    @POST(get_shop_review)
    Call<GetRewiewList> get_shop_review(
            @Field("provider_id") String provider_id
    );

    @FormUrlEncoded
    @POST(get_shop_by_category)
    Call<CourseModal> get_shop_by_category(
            @Field("category_id") String category_id
    );

    @FormUrlEncoded
    @POST(shop_details)
    Call<serviceDetailsModel> shop_details(
                @Field("id") String id,
                @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST(add_like_provider_video)
    Call<ResponseBody> add_like_provider_video(
                @Field("user_id") String user_id,
                @Field("video_id") String video_id
    );

    @FormUrlEncoded
    @POST(update_count)
    Call<ResponseBody> update_count(
            @Field("user_id") String user_id,
            @Field("video_id") String video_id
    );

    @FormUrlEncoded
    @POST(add_rating)
    Call<ResponseBody> add_rating(
            @Field("user_id") String user_id,
            @Field("provider_id") String provider_id,
            @Field("review") String review,
            @Field("rating") String rating
    );

  @FormUrlEncoded
    @POST(get_service_price)
    Call<PaymentModel> get_service_price(
            @Field("service_id") String id
    );

  @FormUrlEncoded
    @POST(get_user_booking)
    Call<MyUserBookingModel> get_user_booking(
            @Field("user_id") String user_id
    );

  @FormUrlEncoded
    @POST(get_provider_booking)
    Call<AllOrdermOdel> get_provider_booking(
            @Field("provider_id") String provider_id,
            @Field("status") String status
    );

  @FormUrlEncoded
    @POST(accepte_reject_booking)
    Call<AcceptedRejectModel> accepte_reject_booking(
            @Field("booking_id") String booking_id,
            @Field("status") String status
    );

  @FormUrlEncoded
    @POST(user_booking)
    Call<BookingModel> user_booking(
            @Field("user_id") String user_id,
            @Field("provider_id") String provider_id,
            @Field("service_id") String service_id,
            @Field("price") String price
    );

    @Multipart
    @POST(update_profile)
    Call<LoginModel>update_profile(
            @Part("user_id") RequestBody user_id    ,
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part MultipartBody.Part part1
    );

    @Multipart
    @POST(provider_signup)
    Call<LoginModel>provider_signup(
            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("mobile") RequestBody mobile,
            @Part("password") RequestBody password,
            @Part("register_id") RequestBody register_id,
            @Part("lat") RequestBody lat,
            @Part("lon") RequestBody lon,
            @Part("type") RequestBody type,
            @Part MultipartBody.Part part1,
            @Part MultipartBody.Part part2,

            @Part("shop_name") RequestBody shop_name,
            @Part("shop_address") RequestBody shop_address,
            @Part("shop_lat") RequestBody shop_lat,
            @Part("shop_lon") RequestBody shop_lon,
            @Part("description") RequestBody description
    );

}
