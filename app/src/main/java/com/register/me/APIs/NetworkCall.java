package com.register.me.APIs;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.model.RegisterModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:00.
 */
public class NetworkCall {

    public void login(ApiInterface apiInterface, String email, String password, String type, NetworkLoginInterface listener) {

        Call<LoginModel> call = apiInterface.getUserLogin(email, password, type);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                Log.d("Success", "" + response.code());
                if (response.code() == 200) {
                    listener.onLoginSuccess(response.body());
                } else {
                    LoginModel.Error errorData = new Gson().fromJson(response.errorBody().charStream(), LoginModel.Error.class);
                    listener.onLoginFailure(errorData.getError());
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Log.d("failure", t.getMessage());
                listener.onLoginFailure(t.getMessage());
            }
        });
    }

    public void signUp(ApiInterface apiInterface, JsonObject data, NetworkSignUpInterface listener) {
        Call<RegisterModel> registerCall = apiInterface.getUserSignUp(data);
        registerCall.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.code() == 200) {
                    Log.d("", "Sucess");
                    listener.onRegisterSuccess(response.body());
                } else {
                    Log.d("", response.code() + "");

                    listener.onRegisterFailure();
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Log.d("", t.getMessage());
                listener.onRegisterFailure();
            }
        });
    }

    public void forgotPassword(ApiInterface apiInterface, String email, NetworkLoginInterface listener) {
        Call<ResponseBody> call = apiInterface.forgotPassword(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    listener.onForgotSuccess();
                } else {
                    listener.onForgotFailure(response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onForgotFailure(t.getMessage());
            }
        });
    }

    public void changePassword(ApiInterface apiInterface, String token, String ctPass, String newPass, NetworkCPInterface listener) {
        Call<ChangePasswordModel> call = apiInterface.changePassword(token, ctPass, newPass);
        call.enqueue(new Callback<ChangePasswordModel>() {
            @Override
            public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
                if (response.code() == 200) {
                    listener.onCPSuccess(response.body());
                } else {
                    listener.onCPFailure(response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                listener.onCPFailure(t.getMessage());
            }
        });
    }

    public void logout(ApiInterface apiInterface, String token, NetworkLogoutInterface listener) {
        Call<LogoutModel> call = apiInterface.logout(token);
        call.enqueue(new Callback<LogoutModel>() {
            @Override
            public void onResponse(Call<LogoutModel> call, Response<LogoutModel> response) {
                if (response.code() == 200) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<LogoutModel> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void updateAvatar(ApiInterface apiInterface, String token, JsonObject encoded, NetworkAvatarInterface listener) {
        Call<AvatarModel> call = apiInterface.updateProfileImage(token, encoded);
        call.enqueue(new Callback<AvatarModel>() {
            @Override
            public void onResponse(Call<AvatarModel> call, Response<AvatarModel> response) {
                if (response.code() == 200) {
                    Log.d("", "Succes");
                    listener.onAvatarSuccess(response.body());
                } else {
                    /*
                    * [size=71 text={"statusCode":400,"status":"BadRequest","message":"Invalid image…]
                    * [size=71 text={"statusCode":400,"status":"BadRequest","message":"Invalid image…]
                    * */
                    listener.onAvatarFailure(response.code() + "");
                }
            }

            @Override
            public void onFailure(Call<AvatarModel> call, Throwable t) {
                listener.onAvatarFailure(t.getMessage());
            }
        });
    }

    public void getUserDetails(ApiInterface apiInterface, String token, NetworkGetUserInfoInterface listener){
        Call<GetUserInfoModel> call = apiInterface.getUserDetails(token);
        call.enqueue(new Callback<GetUserInfoModel>() {
            @Override
            public void onResponse(Call<GetUserInfoModel> call, Response<GetUserInfoModel> response) {
                if(response.code()==200){
                    listener.onUserDataFetched(response.body());
                }else {
                    listener.onUserDataFailure(response.code()+"");
                }
            }

            @Override
            public void onFailure(Call<GetUserInfoModel> call, Throwable t) {
                listener.onUserDataFailure(t.getMessage());
            }
        });

    }

    public interface NetworkLoginInterface {

        void onLoginSuccess(LoginModel body);

        void onLoginFailure(String message);

        void onForgotSuccess();

        void onForgotFailure(String s);
    }

    public interface NetworkSignUpInterface {

        void onRegisterSuccess(RegisterModel body);

        void onRegisterFailure();
    }

    public interface NetworkCPInterface {
        void onCPSuccess(ChangePasswordModel body);

        void onCPFailure(String message);
    }

    public interface NetworkLogoutInterface {
        void onSuccess(LogoutModel body);

        void onFailure(String message);
    }

    public interface NetworkAvatarInterface {
        void onAvatarSuccess(AvatarModel body);

        void onAvatarFailure(String s);
    }

    public interface NetworkGetUserInfoInterface {
        void onUserDataFetched(GetUserInfoModel body);

        void onUserDataFailure(String s);
    }
}
