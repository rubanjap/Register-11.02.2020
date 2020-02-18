package com.register.me.presenter;

import android.content.Context;
import android.util.Patterns;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.LoginModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.HashMap;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 15-02-2020AM 11:01.
 */
public class LoginPresenter implements NetworkCall.NetworkLoginInterface, Utils.UtilAlertInterface {

    private Context context;
    private ILogin loginListener;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    private ApiInterface apiInterface;

    public void init(Context context, ILogin loginListener) {
        this.context = context;
        this.loginListener = loginListener;
        ((BaseActivity) context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public boolean isLoggedIn() {
        String status = repo.getData(constants.CACHE_IS_LOGGED);
        if (status != null && status.equals("true")) {
            return true;
        }
        return false;
    }

    public void validation(String email, String password) {
        boolean valid = Patterns.EMAIL_ADDRESS.matcher(email).matches();
        if (email.isEmpty()) {
            loginListener.showErroMessage(context.getResources().getString(R.string.empty_email_alert));

        } else if (password.isEmpty()) {
            loginListener.showErroMessage(context.getResources().getString(R.string.empty_password_alert));
        } else if (valid) {
            loginListener.showProgress();
            apiCall(email, password);
        } else {
            loginListener.showErroMessage(context.getResources().getString(R.string.valid_email_alert));
        }

    }

    private void apiCall(String email, String password) {

        if (utils.isOnline(context)) {
            networkCall.login(apiInterface, email, password, "password", this);
        } else {
            loginListener.showErroMessage(context.getResources().getString(R.string.network_alert));
        }

    }

    public void forgotPassword() {
        utils.showAlert(context, 2, this);
    }

    @Override
    public void onLoginSuccess(LoginModel body) {
        extractSuccessResponse(body);
        loginListener.dismissProgress();
    }

    private void extractSuccessResponse(LoginModel body) {
        String userName = body.getUserName();
        String userRole = body.getRoles();
        String token = body.getToken();
        String tokenType = body.getTokenType();
        HashMap<String, String> map = new HashMap<>();
        map.put(constants.CACHE_IS_LOGGED, "true");
        map.put(constants.CACHE_USERNAME, userName);
        map.put(constants.CACHE_ROLE, userRole);
        map.put(constants.CACHE_TOKEN, token);
        map.put(constants.CACHE_TOKEN_TYPE, tokenType);
        repo.storeBulkData(map);
        if (userRole.equals("Client")) {
            constants.setUSER_ROLE(0);
        } else {
            constants.setUSER_ROLE(1);
        }
        loginListener.navigate();
    }


    @Override
    public void onLoginFailure(String message) {
        loginListener.dismissProgress();
        loginListener.showErroMessage(message);
    }

    @Override
    public void onForgotSuccess() {
        loginListener.dismissProgress();
        utils.showAlert(context, 3, this);
    }

    @Override
    public void onForgotFailure(String s) {
        loginListener.dismissProgress();
        loginListener.showErroMessage(s);
    }

    @Override
    public void alertResponse(String success) {
        String value = "";
        if (success.contains("$EMAIL$")) {
            value = success.replace("$EMAIL$", "");
            success = "$EMAIL$";
        } else if (success.contains("$ERROR$")) {
            value = success.replace("$ERROR$", "");
            success = "$ERROR$";
        }
        switch (success) {
            case "$EMAIL$":
                if (utils.isOnline(context)) {
                    loginListener.showProgress();
                    networkCall.forgotPassword(apiInterface, value, this);
                } else {
                    loginListener.showErroMessage(context.getResources().getString(R.string.network_alert));
                }
                break;
            case "$ERROR$":
                loginListener.showErroMessage(value);
                break;

        }
    }


    public interface ILogin {
        void showErroMessage(String message);

        void showProgress();

        void dismissProgress();

        void navigate();

    }
}
