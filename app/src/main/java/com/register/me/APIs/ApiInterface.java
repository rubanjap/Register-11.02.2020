package com.register.me.APIs;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.google.gson.JsonObject;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.model.RegisterModel;
import com.register.me.model.data.repository.CacheRepo;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 10:33.
 */
public interface ApiInterface {
    @POST("login")
    @FormUrlEncoded
    Call<LoginModel> getUserLogin(@Field("userName") String username,
                                    @Field("password") String password,
                                    @Field("grant_type") String type);

    @POST("user")
    Call<RegisterModel> getUserSignUp(@Body JsonObject data);

    @GET("forgotpassword")
    Call<ResponseBody> forgotPassword(@Query("email") String emailAddress);

    @GET("changepassword")
    Call<ChangePasswordModel> changePassword(@Header ("Authorization")String token, @Query("oldpwd") String old, @Query("newpwd") String newPass);

    @POST("logout")
    Call<LogoutModel> logout (@Header("Authorization") String token);

    @POST("useravatar")
    Call<AvatarModel> updateProfileImage(@Header("Authorization") String token, @Body JsonObject encoded);

    @GET("user")
    Call<GetUserInfoModel> getUserDetails(@Header("Authorization") String token);
}
