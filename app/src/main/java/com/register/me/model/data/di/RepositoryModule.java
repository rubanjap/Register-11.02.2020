package com.register.me.model.data.di;

import android.content.SharedPreferences;


import com.register.me.model.data.repository.CacheRepositoryImpl;
import com.register.me.model.domain.repository.CacheRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides
    public CacheRepository provideCacheRepository(SharedPreferences sharedPreferences) {
        return new CacheRepositoryImpl(sharedPreferences);
    }
}
