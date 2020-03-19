package com.register.me.presenter;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.model.UpdateProfileModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 18-02-2020PM 04:45.
 */
public class PersonalInfoPresenter implements ClientNetworkCall.NetworkCallInterface, Utils.UtilAlertInterface {

    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    JsonBuilder builder;
    private Context context;
    private IPersonalInfo listener;

    public void init(Context context, IPersonalInfo listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
    }

    public int getRole() {
        return constants.getuserRole();
    }

    public void getUserDetails() {
       /* String data = repo.getData(constants.getCACHE_USER_INFO());
        if(data!=null&&!force){
            GetUserInfoModel body = new Gson().fromJson(data, GetUserInfoModel.class);
            onUserDataFetched(body);
            return;
        }*/
        if (utils.isOnline(context)) {
            listener.showProgress();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            networkCall.getUserDetails(apiInterface, token, this);
        } else {
            listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    public void validate(ArrayList<QandA> info) {
        for (QandA item : info) {
            if (item.getAnswer() == null || item.getAnswer().isEmpty()) {
                listener.showErrorMessage("Please enter " + item.getQuestion());
                return;
            }
        }
        updateUser(info);
    }

    private void updateUser(ArrayList<QandA> info) {
        JsonObject data = builder.getUserUpdateJson(info);
        Log.d("data", data.toString());
        apiCall(data);
    }

    private void apiCall(JsonObject data) {
        if (utils.isOnline(context)) {
            listener.showProgress();
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            networkCall.updateUserInformation(apiInterface, token, data, this);
        } else {
            listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }


    @Override
    public void alertResponse(String success) {
        listener.exitScreen();
    }

    public int getInputType(int inputType) {
        return utils.getInputType(inputType);
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        if (response instanceof UpdateProfileModel) {
            utils.showAlert(context, 6, this);
        } else if (response instanceof GetUserInfoModel) {
            GetUserInfoModel body = ((GetUserInfoModel) response);
            GetUserInfoModel.User user = body.getData().getUser();
            ArrayList<QandA> info = new ArrayList<QandA>();
            repo.storeData(constants.getCACHE_USER_INFO(), new Gson().toJson(body));

            /*
             * 1 - text
             * 2 - email
             * 3 - password
             * 4 - number
             * */
            /*
             * 1 - action_next;
             * 2 - action_done
             * 2 - password
             * */

            info.add(new QandA("First Name", user.getFirstname(), 1, 1, 1, "firstname", null, null));
            info.add(new QandA("Last Name", user.getLastName(), 1, 1, 1, "lastname", null, null));
            info.add(new QandA("Email", user.getEmailAddress(), 1, 2, 1, "email", null, null));
            info.add(new QandA("Telephone", user.getTelephone(), 1, 4, 1, "telephone", null, null));
            info.add(new QandA("Cell Phone", user.getCellPhone(), 1, 4, 2, "cellphone", null, null));
             if (getRole() == 1) {
                info.add(new QandA("Company Name", "Test Company", 1, 1, 1, "text_key", null, null));
                info.add(new QandA("Address 1", "Test Address", 1, 1, 1, "text_key", null, null));
                info.add(new QandA("Address 1", "Test Address", 1, 1, 1, "text_key", null, null));
                info.add(new QandA("City", "Test City", 1, 1, 1, "text_key", null, null));
                info.add(new QandA("State", "Test State", 1, 1, 1, "text_key", null, null));
                info.add(new QandA("Postal Code", "Test Postal", 1, 1, 1, "text_key", null, null));
                info.add(new QandA("Country", "Test Country", 1, 1, 1, "text_key", null, null));
            }
            info.add(new QandA("Notification", String.valueOf(user.getEmailNotification()), 2, 0, 0, "notification", null, null));


            listener.updateUI(info);
        }
    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showErrorMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.dismissProgress();
        listener.showErrorMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context);
    }


    public interface IPersonalInfo {

        void showErrorMessage(String mesage);

        void updateUI(ArrayList<QandA> body);

        void errorFetchingData(String s);

        void exitScreen();

        void dismissProgress();

        void showProgress();
    }
}
