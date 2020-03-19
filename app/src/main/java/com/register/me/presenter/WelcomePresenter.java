package com.register.me.presenter;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;

import com.google.gson.Gson;
import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.Error;
import com.register.me.model.data.model.GetUserInfoModel;
import com.register.me.model.data.model.Steps;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Jennifer - AIT on 25-02-2020PM 01:23.
 */
public class WelcomePresenter implements ClientNetworkCall.NetworkCallInterface {

    Context context;

    @Inject
    Constants constants;
    @Inject
    CacheRepo repo;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    Utils utils;
    private Observer<String> messageObserver;


    public void init(Context context, Observer<String> message) {
        this.context = context;
        messageObserver = message;
        ((BaseActivity) context).injector().inject(this);
    }

    public void getUserProfile() {
        String data = repo.getData(constants.getCACHE_USER_INFO());
        if (data == null) {
            apiUserProfileCall();
        }
    }

    private void apiUserProfileCall() {
        if (utils.isOnline(context)) {
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            networkCall.getUserDetails(apiInterface, token, this);
        } else {
            KToast.customColorToast((Activity) context, context.getResources().getString(R.string.network_alert), Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
        }
    }

    @Override
    public void onCallSuccess(Object response) {
        if (response instanceof GetUserInfoModel) {
            GetUserInfoModel body = ((GetUserInfoModel) response);
            repo.storeData(constants.getCACHE_USER_INFO(), new Gson().toJson(body));
        }
    }

    @Override
    public void onCallFail(String message) {
        KToast.customColorToast((Activity) context, message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);

    }

    @Override
    public void sessionExpired() {
        KToast.customColorToast((Activity) context, "Session Expired", Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context);
    }

    public void getStepStatus(Observer<Integer> getStepObserver) {
        ApiInterface retrofitBuilder = retrofit.create(ApiInterface.class);
        Observable<Response<Steps>> observable = retrofitBuilder.getStepStatus(repo.getData(constants.getcacheTokenKey()))
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation()).
                        observeOn(AndroidSchedulers.mainThread());
        Disposable subscribe = observable.subscribe(stepsResponse -> {
            int code = stepsResponse.code();
            Observable<String> messageObs;
            switch (code) {
                case 200:
//                    Observable<Integer> obs = Observable.just(stepsResponse.body().getData().getCurrentstep());
                    Observable<Integer> obs = Observable.just(2);
                    obs.subscribe(getStepObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(stepsResponse.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    messageObs = Observable.just(message);
                    messageObs.subscribe(messageObserver);
                    break;
                default:
                    throw new IllegalStateException("Unexpected Error: " + code);
            }
        }, error -> {

        });

    }

}
