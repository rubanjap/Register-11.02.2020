package com.register.me.Adapter;

import com.register.me.view.BaseFragment;

import dagger.Module;
import dagger.Provides;

@Module
public class AdapterModule {
    @Provides
    ProductAdapter provideProductStatusAdapter() {
        return new ProductAdapter();
    }

    @Provides
    AuctionAdapter provideAuctionAdapter() {
        return new AuctionAdapter();
    }

    @Provides
    ProjectAdapter provideProjectAdapter() {
        return new ProjectAdapter();
    }

}
