package com.register.me.APIs;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.ApplicationRRESubmission;
import com.register.me.model.data.model.Error;
import com.register.me.model.data.model.RREApplication;
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
import okhttp3.internal.Util;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RRENetworkCall {
    @Inject
    Retrofit retrofit;
    @Inject
    CacheRepo repo;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    private Observer<String> messageObserver;
    private Context context;
    private ApiInterface retrofitBuilder;
    private String token;

    public void init(Context context, Observer<String> message) {
        this.context = context;
        messageObserver = message;
        ((BaseActivity) context).injector().inject(this);
        retrofitBuilder = retrofit.create(ApiInterface.class);
        token = repo.getData(constants.getcacheTokenKey());
    }

    public void getStepStatus(Observer<Integer> getStepObserver) {
        Observable<Response<Steps>> observable = retrofitBuilder.getStepStatus(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation()).
                        observeOn(AndroidSchedulers.mainThread());

        Disposable dis = observable.subscribe(stepsResponse -> {
            int code = stepsResponse.code();

            switch (code) {
                case 200:
                    assert stepsResponse.body() != null;
//                    Observable<Integer> obs = Observable.just(stepsResponse.body().getData().getCurrentstep());
                    Observable<Integer> obs = Observable.just(3);
                    obs.subscribe(getStepObserver);

                    break;
                case 401:
                    Error errorData = new Gson().fromJson(stepsResponse.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired(message);
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
                    throw new IllegalStateException("Unexpected Error: " + code);
            }
        }, error -> {
            errorMessage(error.getMessage());
        });
    }


    public void viewRREApplication(Observer<RREApplication> getApplicationObserver) {


        Observable<Response<RREApplication>> observable = retrofitBuilder.getRREApplication(token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());
        Disposable subscribe = observable.subscribe(response -> {
                    int code = response.code();
                    switch (code) {
                        case 200:
                            assert response.body() != null;
                            Observable<RREApplication> obs = Observable.just(response.body());
                            obs.subscribe(getApplicationObserver);
                            break;
                        case 401:
                            Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                            String message = errorData.getMessage();
                            sessionExpired(message);
                            break;
                        default:
                            errorMessage("Unexpected Error: " + code);
                            throw new IllegalStateException("Unexpected Error: " + code);

                    }
                },
                error -> {
                    errorMessage(error.getMessage());
                });
    }

    public void submitRREApplication(JsonObject data, Observer<ApplicationRRESubmission> submitObserver) {
        Observable<Response<ApplicationRRESubmission>> observable = retrofitBuilder.submitRREApplicationForm(token, data)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread());

        Disposable subscribe = observable.subscribe(response -> {

            int code = response.code();
            switch (code) {
                case 200:
                    assert response.body() != null;
                    Observable<ApplicationRRESubmission> obs = Observable.just(response.body());
                    obs.subscribe(submitObserver);
                    break;
                case 401:
                    Error errorData = new Gson().fromJson(response.errorBody().charStream(), Error.class);
                    String message = errorData.getMessage();
                    sessionExpired(message);
                    break;
                default:
                    errorMessage("Unexpected Error: " + code);
                    throw new IllegalStateException("Unexpected Error: " + code);

            }
        }, error -> {
        });
    }

    private void errorMessage(String s) {
        Observable<String> messageObs = Observable.just(s);
        messageObs.subscribe(messageObserver);
    }

    private void sessionExpired(String message) {
        Observable<String> messageObs = Observable.just("Session Expired");

        repo.storeData(constants.getcacheIsLoggedKey(), "false");
        repo.storeData(constants.getCACHE_USER_INFO(), null);
        utils.sessionExpired(context);
        messageObs.subscribe(messageObserver);
    }


}
