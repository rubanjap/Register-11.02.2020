package com.register.me.presenter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.util.Base64;

import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AvatarModel;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.LogoutModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import retrofit2.Retrofit;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class HomePresenter implements Utils.UtilAlertInterface, NetworkCall.NetworkLogoutInterface, NetworkCall.NetworkAvatarInterface {

    private final int PICK_FROM_GALLERY = 100;
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
                    constants.setSelectedList(null);
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

    public void logout() {
        utils.showAlert(context, 5, this);
    }


    @Override
    public void alertResponse(String status) {
        if (status.equals("$LOGOUT")) {
            if (utils.isOnline(context)) {
                String token = repo.getData(constants.getCACHE_TOKEN());
                networkCall.logout(apiInterface, token, this);
            }

        } else {
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
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            ((Activity) context).startActivityForResult(intent, PICK_FROM_GALLERY);
        }
    }

    public void apiUpdateAvatar(Bitmap bitmap) {
        if (utils.isOnline(context)) {
            /*  Compress Actual Image   */
            Bitmap compressedBmp = getResizedBitmap(bitmap, 400);

            /*   Convert bitmap  to base 64 */
            String encoded = getEncodedString(compressedBmp);

            String token = repo.getData(constants.getCACHE_TOKEN());
            JsonObject jObj = builder.getAvatarJson(encoded);
            networkCall.updateAvatar(apiInterface, token, jObj, this);
        } else {
            view.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    private String getEncodedString(Bitmap compressedBmp) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compressedBmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    @Override
    public void onAvatarSuccess(AvatarModel body) {
        String url = body.getUrl();
        if (url != null) {
            repo.storeData(constants.getCACHE_USER_PROFILE_URL(), url);
            view.updateProfileImage(url);
        }
    }

    @Override
    public void onAvatarFailure(String s) {
        view.showErrorMessage(context.getResources().getString(R.string.avatar_update_failed));
    }

    public String getProfileImage() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        GetUserInfoModel jS = new Gson().fromJson(data, GetUserInfoModel.class);
        return jS.getData().getUser().getImageUrl();
    }

    public String getUserName() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        GetUserInfoModel jS = new Gson().fromJson(data, GetUserInfoModel.class);


        return jS.getData().getUser().getName();
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }


    public interface View {

        void showClientDashBoard();

        void showNewProject();

        void showRRE_DashBoard();

        void showOnlineInter();

        void showErrorMessage(String message);

        void logout();

        void updateProfileImage(String url);
    }
}

