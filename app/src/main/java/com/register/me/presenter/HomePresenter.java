package com.register.me.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Network;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import retrofit2.Retrofit;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class HomePresenter implements FragmentPresenter, Utils.UtilAlertInterface, NetworkCall.NetworkLogoutInterface, NetworkCall.NetworkAvatarInterface {

    private static final int PICK_FROM_GALLERY = 100;
    private View view;
    @Inject
    Constants constants;
    private Context context;
    @Inject
    Utils utils;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    JsonBuilder builder;
    private ApiInterface apiInterface;

    public HomePresenter() {

    }

    public void init(Context context, String from) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
        apiInterface = retrofit.create(ApiInterface.class);
        int role = constants.getUSER_ROLE();
        int tab = constants.getTAB();
        switch (role) {
            case 0:
                if (tab == 1) {
                    view.showNewProject();
                } else if (tab == 4) {
                    view.showClientDashBoard();
                }
                break;
            case 1:
                if (tab == 1) {
                    view.showRRE_DashBoard();
                } else if (tab == 2) {
                    view.showOnlineInter();
                }
                break;

        }
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void dispose() {

    }

    @Override
    public void restore() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

    }

    @Override
    public void onRestoreInstanceState(Bundle outState) {

    }

    public void logout() {
        utils.showAlert(context, 5, this);
    }

    @Override
    public void alertResponse(String status) {
        if (status.equals("$LOGOUT")) {
            if(utils.isOnline(context)){
            String token = repo.getData(constants.getCACHE_TOKEN());
            networkCall.logout(apiInterface, token, this);}

        }else {
            view.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onSuccess(LogoutModel body) {
        String message = body.getData().getMessage();
        Integer statusCode = body.getStatusCode();
        if (statusCode != null && statusCode == 200) {
            repo.storeData(constants.getCACHE_IS_LOGGED(), "false");
            view.logout();
        }

    }

    @Override
    public void onFailure(String message) {
//        view.showErrorMessage("Unable to Logout");
        repo.storeData(constants.getCACHE_IS_LOGGED(), "false");
        view.logout();
    }

    public void pickImage() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            ((Activity)context).startActivityForResult(intent, PICK_FROM_GALLERY);
        }
    }

    public void apiUpdateAvatar(String encoded) {
        String token = repo.getData(constants.getCACHE_TOKEN());
        JsonObject jObj = builder.getAvatarJson(encoded);
        networkCall.updateAvatar(apiInterface,token,jObj,this);
    }

    @Override
    public void onAvatarSuccess(AvatarModel body) {
        String url =body.getUrl();
        if(url!=null){
            repo.storeData(constants.getCACHE_USER_PROFILE_URL(),url);
            view.updateProfileImage(url);
        }
    }

    @Override
    public void onAvatarFailure(String s) {

    }

    public String getProfileImage() {
        return repo.getData(constants.getCACHE_USER_PROFILE_URL());
    }


    public interface View extends FragmentPresenter.View {

        void showClientDashBoard();

        void showNewProject();

        void showRRE_DashBoard();

        void showOnlineInter();

        void showErrorMessage(String message);

        void logout();

        void updateProfileImage(String url);
    }
}

