package com.register.me.presenter;

import android.content.Context;
import android.os.Bundle;

import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class PortFolioPresenter implements NetworkCall.NetworkProductInterface {
    private View view;
    private Context context;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkCall networkCall;
    @Inject
    Constants constants;
    @Inject
    Utils utils;
    @Inject
    CacheRepo repo;



    public void init(View view, Context context) {
        this.view = view;
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }



    public void getList() {
        if (utils.isOnline(context)) {
            view.showProgress();
            String token = repo.getData(constants.getCACHE_TOKEN());
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            networkCall.getProductList(apiInterface, token, this);
        } else {
            view.hideProgress();
            view.showErroMessage(context.getResources().getString(R.string.network_alert));
        }

    }

    @Override
    public void onProductListSuccess(GetProductModel body) {
        List<GetProductModel.Product> list = body.getData().getProducts();
        constants.setBASE_PRODUCT_LIST(list);
        view.hideProgress();
        view.updateList(list);
    }

    @Override
    public void onProductListFailed(String s) {
        view.hideProgress();
        view.showErroMessage(s);
    }

    public List<GetProductModel.Product> checkData() {
       return constants.getBASE_PRODUCT_LIST()!= null ? constants.getBASE_PRODUCT_LIST() : null;
    }

    public interface View {

        void showErroMessage(String message);

        void updateList(List<GetProductModel.Product> list);

        void showProgress();

        void hideProgress();
    }
}
