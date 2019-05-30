package com.example.online_clothing_app_4th_assignment.API;

import com.example.online_clothing_app_4th_assignment.LoginFragment;
import com.example.online_clothing_app_4th_assignment.Models.UserModel;
import com.example.online_clothing_app_4th_assignment.RegisterFragment;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MasterApi {
    @FormUrlEncoded
    @POST("users/login")
    Call<Void> checkUser(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("Users/signup")
    Call<Void> registerUser(UserModel userModel);

}
