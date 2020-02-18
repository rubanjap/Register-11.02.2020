package com.register.me.presenter;

import android.content.Context;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 18-02-2020PM 04:45.
 */
public class PersonalInfoPresenter implements NetworkCall.NetworkGetUserInfoInterface {

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
    private Context context;
    private IPersonalInfo listener;

    public void init(Context context, IPersonalInfo listener){
        this.context = context;
        this.listener = listener;
        ((BaseActivity)context).injector().inject(this);
    }

    public void getUserDetails(){
        if(utils.isOnline(context)){
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.CACHE_TOKEN);
            networkCall.getUserDetails(apiInterface,token,this);
        }
        else {
          listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    @Override
    public void onUserDataFetched(GetUserInfoModel body) {
        GetUserInfoModel.User user = body.getData().getUser();
        ArrayList<QandA> info = new ArrayList<QandA>();
        info.add(new QandA("First Name",user.getName(),1));
        info.add(new QandA("Last Name",user.getName(),1));
        info.add(new QandA("Email",user.getEmailAddress(),1));
        info.add(new QandA("Telephone",user.getTelephone(),1));
        info.add(new QandA("Cell Phone",user.getCellPhone(),1));
        info.add(new QandA("Notification",String.valueOf(user.getEmailNotification()),2));

        listener.updateUI(info);
    }

    @Override
    public void onUserDataFailure(String s) {
listener.errorFetchingData(s);
    }

    public interface IPersonalInfo{

        void showErrorMessage(String mesage);

        void updateUI(ArrayList<QandA> body);

        void errorFetchingData(String s);
    }
}
