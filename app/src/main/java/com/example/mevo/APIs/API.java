package com.example.mevo.APIs;

import com.example.mevo.DataModels.UserModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {
    @POST("signup")
    Call<UserModel> signUp(@Body UserModel dataModal);
    @POST("signin")
    Call<UserModel> signIn(@Body UserModel model);
}
