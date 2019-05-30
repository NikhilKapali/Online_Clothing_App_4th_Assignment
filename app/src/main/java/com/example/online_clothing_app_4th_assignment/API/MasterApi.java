package com.example.online_clothing_app_4th_assignment.API;

import com.example.online_clothing_app_4th_assignment.LoginFragment;
import com.example.online_clothing_app_4th_assignment.Models.ItemImg;
import com.example.online_clothing_app_4th_assignment.Models.UserModel;
import com.example.online_clothing_app_4th_assignment.RegisterFragment;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface MasterApi {
    @FormUrlEncoded
    @POST("login")
    Call<Void> checkUser(@Field("username") String username, @Field("password") String password);

    @POST("signup")
    Call<Void> registerUser(@Body UserModel userModel);

    @Multipart
    @POST("uploadImage")
    Call<ItemImg> uploadImage(@Part MultipartBody.Part multipartBody);

}
