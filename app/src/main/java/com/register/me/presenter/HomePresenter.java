package com.register.me.presenter;

import android.content.Context;
import android.os.Bundle;

import com.register.me.model.data.Constants;
import com.register.me.model.domain.repository.CacheRepository;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

public class HomePresenter implements FragmentPresenter {

    private final CacheRepository cacheRepository;
    private View view;
    @Inject
    Constants constants;
    private Context context;

    public HomePresenter(CacheRepository cacheRepository) {
        this.cacheRepository = cacheRepository;
    }

    public void init(Context context,String from) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
        int role = constants.getUSER_ROLE();
        switch (role) {
            case 0:
                if (from != null && from.equals("NEW_PROJECT")) {
                    view.showNewProject();
                } else {
                    view.showClientDashBoard();
                }
                break;
            case 1:
                view.showRRE_DashBoard();
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

    public interface View extends FragmentPresenter.View {

        void showClientDashBoard();

        void showNewProject();

        void showRRE_DashBoard();
    }
}

