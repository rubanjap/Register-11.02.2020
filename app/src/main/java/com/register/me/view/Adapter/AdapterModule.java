package com.register.me.view.Adapter;

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
