package com.register.me.model.di.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.model.data.Constants;
import com.register.me.model.data.util.Util;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


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
    public Util utils(){return new Util();}
}
