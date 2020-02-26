package com.register.me.APIs;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.ChangePasswordModel;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.model.RegisterModel;
import com.register.me.model.data.model.UpdateProfileModel;
import com.register.me.model.data.repository.CacheRepo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:00.
 */
public class NetworkCall {

    String serverDown = "Unable to connect! \n Sever is down at the moment";

    public void login(ApiInterface apiInterface, String email, String password, String type, NetworkLoginInterface listener) {

        Call<LoginModel> call = apiInterface.getUserLogin(email, password, type);
        call.enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                int code = response.code();

                switch (code) {
                    case 200:
                        listener.onLoginSuccess(response.body());
                        break;
                    case 500:
                        listener.onLoginFailure(serverDown);
                        break;
                    default:
                        try {
                            LoginModel.Error errorData = new Gson().fromJson(response.errorBody().charStream(), LoginModel.Error.class);
                            listener.onLoginFailure(errorData.getError());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
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
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onRegisterSuccess(response.body());
                        break;
                    case 500:
                        listener.onRegisterFailure(serverDown);
                        break;
                    default:
                        listener.onRegisterFailure(code + "");
                        break;
                }


            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Log.d("", t.getMessage());
                listener.onRegisterFailure(t.getMessage());
            }
        });
    }

    public void forgotPassword(ApiInterface apiInterface, String email, NetworkLoginInterface listener) {
        Call<ResponseBody> call = apiInterface.forgotPassword(email);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onForgotSuccess();
                        break;
                    case 500:
                        listener.onForgotFailure(serverDown);
                        break;
                    default:
                        listener.onForgotFailure(code + "");

                        break;
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

                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onCPSuccess(response.body());
                        break;
                    case 500:
                        listener.onCPFailure(serverDown);
                        break;
                    default:
                        listener.onCPFailure(code + "");
                        break;
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


                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onSuccess(response.body());
                        break;
                    case 500:
                        listener.onFailure(response.code() + "");
                        break;
                    default:
                        listener.onFailure(response.code() + "");
                        break;
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

                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onAvatarSuccess(response.body());
                        break;
                    case 500:
                        listener.onAvatarFailure(serverDown);
                        break;
                    default:
                        /*
                         * [size=71 text={"statusCode":400,"status":"BadRequest","message":"Invalid image…]
                         * [size=71 text={"statusCode":400,"status":"BadRequest","message":"Invalid image…]
                         * */
                        listener.onAvatarFailure(code + "");
                        break;
                }


            }

            @Override
            public void onFailure(Call<AvatarModel> call, Throwable t) {
                listener.onAvatarFailure(t.getMessage());
            }
        });
    }

    public void getUserDetails(ApiInterface apiInterface, String token, NetworkGetUserInfoInterface listener) {
        Call<GetUserInfoModel> call = apiInterface.getUserDetails(token);
        call.enqueue(new Callback<GetUserInfoModel>() {
            @Override
            public void onResponse(Call<GetUserInfoModel> call, Response<GetUserInfoModel> response) {


                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onUserDataFetched(response.body());
                        break;
                    case 500:
                        listener.onUserDataFailure(serverDown);
                        break;
                    default:
                        listener.onUserDataFailure(response.code() + "");
                        break;
                }

            }

            @Override
            public void onFailure(Call<GetUserInfoModel> call, Throwable t) {
                listener.onUserDataFailure(t.getMessage());
            }
        });

    }

    public void updateUserInformation(ApiInterface apiInterface, String token, JsonObject data, NetworkUpdateUserInterface listener) {
        Call<UpdateProfileModel> call = apiInterface.updateUserProfile(token, data);
        call.enqueue(new Callback<UpdateProfileModel>() {
            @Override
            public void onResponse(Call<UpdateProfileModel> call, Response<UpdateProfileModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onUserUpdateSuccess();
                        break;
                    case 500:
                        listener.onUserUpdateFailed(serverDown);
                        break;
                    default:
                        listener.onUserUpdateFailed(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<UpdateProfileModel> call, Throwable t) {
                listener.onUserUpdateFailed(t.getMessage());
            }
        });
    }

    public void getProductList(ApiInterface apiInterface, String token, NetworkProductInterface listener) {
        Call<GetProductModel> call = apiInterface.getProductList(token);
        call.enqueue(new Callback<GetProductModel>() {
            @Override
            public void onResponse(Call<GetProductModel> call, Response<GetProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onProductListSuccess(response.body());
                        break;
                    case 500:
                        listener.onProductListFailed(serverDown);
                        break;
                    default:
                        listener.onProductListFailed(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<GetProductModel> call, Throwable t) {
                listener.onProductListFailed(t.getMessage());
            }
        });

    }

    public void addProduct(ApiInterface apiInterface,String token,JsonObject data,NetworkAddProductInterface listener){
        Call<AddProductModel> call = apiInterface.addProduct(token, data);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onProductAddedSuccess(response.body());
                        break;
                    case 500:
                        listener.onProductAddedFailed(serverDown);
                        break;
                    default:
                        listener.onProductAddedFailed(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                listener.onProductAddedFailed(t.getMessage());
            }
        });
    }


    public void editProduct(ApiInterface apiInterface, String token, JsonObject data, Integer productId, NetworkAddProductInterface listener){
        Call<AddProductModel> call =apiInterface.editProduct(token,productId,data);
        call.enqueue(new Callback<AddProductModel>() {
            @Override
            public void onResponse(Call<AddProductModel> call, Response<AddProductModel> response) {
                int code = response.code();
                switch (code) {
                    case 200:
                        listener.onProductUpdatedSuccess(response.body());
                        break;
                    case 500:
                        listener.onProductUpdatedFailed(serverDown);
                        break;
                    default:
                        listener.onProductUpdatedFailed(code + "");
                        break;
                }
            }

            @Override
            public void onFailure(Call<AddProductModel> call, Throwable t) {
                listener.onProductUpdatedFailed(t.getMessage());
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

        void onRegisterFailure(String message);
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

    public interface NetworkUpdateUserInterface {
        void onUserUpdateSuccess();

        void onUserUpdateFailed(String s);
    }

    public interface NetworkProductInterface {
        void onProductListSuccess(GetProductModel body);

        void onProductListFailed(String s);
    }

    public interface NetworkAddProductInterface {
        void onProductAddedSuccess(AddProductModel body);

        void onProductAddedFailed(String s);

        void onProductUpdatedSuccess(AddProductModel body);

        void onProductUpdatedFailed(String s);
    }
}
