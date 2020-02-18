package com.register.me.model.di.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.register.me.APIs.NetworkCall;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


@Singleton
@Module
public class AndroidModule {
    private final Context context;

    public AndroidModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    @Named(Constants.APPLICATION_CONTEXT)
    public Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(@Named(Constants.APPLICATION_CONTEXT) Context context) {
        return context.getSharedPreferences("skit_pref", Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Resources provideResources(@Named(Constants.APPLICATION_CONTEXT) Context context) {
        return context.getResources();
    }

    @Singleton
    @Provides
    public Constants provideConstants(){
        return new Constants();
    }

    @Singleton
    @Provides
    public Utils utils(){return new Utils();}

    @Singleton
    @Provides
    public JsonBuilder provideJsonBuilder(){
        return new JsonBuilder();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(){
        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(120, TimeUnit.SECONDS)
                .connectTimeout(180,TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        return new Retrofit.Builder()
                .baseUrl(Constants.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    @Singleton
    @Provides
    NetworkCall provideNetworkCall(){
        return new NetworkCall();
    }

    @Singleton
    @Provides
    CacheRepo provideCacheRepo(@Named(Constants.APPLICATION_CONTEXT) Context context){
        return new CacheRepo(context);
    }
}
