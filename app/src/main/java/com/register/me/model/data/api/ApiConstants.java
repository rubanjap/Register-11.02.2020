package com.register.me.model.data.api;


import com.register.me.BuildConfig;


public class ApiConstants {

    public static String BASE_URL = BuildConfig.env + BuildConfig.domain + BuildConfig.apiVersion;
    public static final int NETWORK_EXCEPTION_CODE = -10;
    public static final int UNKNOWN_EXCEPTION_CODE = -11;
}
