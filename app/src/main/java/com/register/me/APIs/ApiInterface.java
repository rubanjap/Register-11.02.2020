package com.register.me.APIs;

import com.google.gson.JsonObject;
import com.register.me.model.data.model.ActiveAuction;
import com.register.me.model.data.model.ActiveCompProject;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.model.CrreList;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LocationModel;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.model.PostReply;
import com.register.me.model.data.model.ProjectModel;
import com.register.me.model.data.model.RegisterModel;
import com.register.me.model.data.model.RequestRegion;
import com.register.me.model.data.model.Steps;
import com.register.me.model.data.model.UpdateProfileModel;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.model.ViewDetails;

import java.util.HashMap;


import javax.annotation.PostConstruct;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

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
    Call<ChangePasswordModel> changePassword(@Header("Authorization") String token, @Query("oldpwd") String old, @Query("newpwd") String newPass);

    @POST("logout")
    Call<LogoutModel> logout(@Header("Authorization") String token);

    @POST("useravatar")
    Call<AvatarModel> updateProfileImage(@Header("Authorization") String token, @Body JsonObject encoded);

    @GET("user")
    Call<GetUserInfoModel> getUserDetails(@Header("Authorization") String token);

    @POST("userprofile")
    Call<UpdateProfileModel> updateUserProfile(@Header("Authorization") String token, @Body JsonObject data);

    @GET("product")
    Call<GetProductModel> getProductList(@Header("Authorization") String token);

    @POST("product")
    Call<AddProductModel> addProduct(@Header("Authorization") String token, @Body JsonObject data);


    @POST("product")
    Call<AddProductModel> editProduct(@Header("Authorization") String token, @Query("productid") int id, @Body JsonObject data);

    @GET("productdetails")
    Call<ViewDetails> viewDetails(@Header("Authorization") String token, @QueryMap HashMap<String, String> data);

    @GET("location")
    Call<LocationModel> getLocation(@Header("Authorization") String token);

    @POST("initiatebidding")
    Call<AddProductModel> initiateBid(@Header("Authorization") String token, @Body JsonObject data);


    @POST("directassignment")
    Call<AddProductModel> directAssign(@Header("Authorization") String token, @Body JsonObject data);

    @POST("projectcancel")
    Call<ResponseBody> cancelProject(@Header("Authorization") String token, @Body JsonObject data);

    @GET("countrylist")
    Call<ProjectModel> getProjectList(@Header("Authorization") String token, @Query("productid") String id);

    @GET("activeprojectauctions")
    Call<ActiveAuction> getAuctionList(@Header("Authorization") String token);

    @GET("activecompletedprojects")
    Call<ActiveCompProject> getACList(@Header("Authorization") String token);

    @GET("activecompleteviews")
    Call<ViewActCompProject> getACDetail(@Header("Authorization") String token, @Query("projectid") String id);

    @POST("regionrequest")
    Call<RequestRegion> requestRegion(@Header("Authorization") String token, @Body JsonObject obj);

    @POST("getcrrelist")
    Call<CrreList> getCrreList(@Header("Authorization") String token, @Body JsonObject obj);

    @POST("postreplymail")
    Call<PostReply> postReply(@Header("Authorization") String token, @Body JsonObject obj);

    @GET("rrprocesssteps")
    Observable<Response<Steps>> getStepStatus(@Header("Authorization") String token);
}
