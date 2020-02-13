package com.register.me.presenter;

import android.os.Bundle;

import com.register.me.model.domain.repository.CacheRepository;

public class HomePresenter implements FragmentPresenter {

    private final CacheRepository cacheRepository;
    private View view;

    public HomePresenter(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public void init() {
        /*if (!cacheRepository.isLoggedIn()) {
            view.showLogin();
        } else {
            view.showHome();
        }*/

        view.showDashBoard();
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

    public interface View extends FragmentPresenter.View {

        void showDashBoard();
    }
}

