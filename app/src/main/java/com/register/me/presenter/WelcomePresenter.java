package com.register.me.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 25-02-2020PM 01:23.
 */
public class WelcomePresenter implements NetworkCall.NetworkGetUserInfoInterface {

    Context context;

    @Inject
    Constants constants;
    @Inject
    CacheRepo repo;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkCall networkCall;


    public void init (Context context) {
        this.context = context;
        ((BaseActivity)context).injector().inject(this);
    }

    public void getUserProfile(){
        String data = repo.getData(constants.getCACHE_USER_INFO());
        if(data==null){
            apiUserProfileCall();
        }
    }

    private void apiUserProfileCall() {
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        String token = repo.getData(constants.getCACHE_TOKEN());
        networkCall.getUserDetails(apiInterface, token, this);
    }

    @Override
    public void onUserDataFetched(GetUserInfoModel body) {
        repo.storeData(constants.getCACHE_USER_INFO(),new Gson().toJson(body));
    }

    @Override
    public void onUserDataFailure(String s) {

    }
}
