package com.register.me.presenter;


import com.register.me.model.data.di.DataModule;
import com.register.me.model.domain.repository.CacheRepository;

import dagger.Module;
import dagger.Provides;


@Module(includes = {DataModule.class})
public class PresenterModule {

    @Provides
    public HomePresenter provideHomePresenter(CacheRepository cacheRepository) {
        return new HomePresenter(cacheRepository);
    }

    @Provides
    public DashBoardPresenter provideDashBoardPresenter() {
        return new DashBoardPresenter();
    }

    @Provides
    public PortFolioPresenter providePortFolioPresenter() {
        return new PortFolioPresenter();
    }




    @Provides
    public AddProductPresenter provideAddProductPresenter() {
        return new AddProductPresenter();
    }

    @Provides
    public ViewProductPresenter provideViewProductPresenter() {
        return new ViewProductPresenter();
    }

    @Provides
    public ActiveProjectPresenter provideActiveProjectPresenter() {
        return new ActiveProjectPresenter();
    }

    @Provides
    public InitiateRegistrationPresenter provideInitiateRegistrationPresenter() {
        return new InitiateRegistrationPresenter();
    }
}
