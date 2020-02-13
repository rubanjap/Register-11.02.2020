package com.register.me.model.data.di;

import com.register.me.model.data.api.AnnotationExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.joda.time.DateTime;

import dagger.Module;
import dagger.Provides;


@Module
public class ApiModule {

    @Provides
    public Gson provideGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Boolean.class, TypeAdapterUtil.getBooleanAdapter())
                .registerTypeAdapter(boolean.class, TypeAdapterUtil.getBooleanAdapter())
                .registerTypeAdapter(DateTime.class, TypeAdapterUtil.getDateTimeAdapter())
                .addSerializationExclusionStrategy(new AnnotationExclusionStrategy())
                .create();
    }
}
