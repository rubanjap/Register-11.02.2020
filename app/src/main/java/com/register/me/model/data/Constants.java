package com.register.me.model.data;


import com.register.me.model.data.model.GetProductModel;

import java.util.List;

public class Constants {

    public static final String ERROR = "error";
    public static final String EMPTY_STRING = "";
    public static final String APPLICATION_CONTEXT = "application";
    public static final String ACTIVITY_CONTEXT = "activity";
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_PER_PAGE = 40;
    public static final String ANDROID_TAG = "android";

    /*
     * 0 - Screen from Project Portfolio
     * 1 - Screen from Active Auction
     * 2 - Screen from Active Projects
     * */
    private int VIEW_SCREEN_FROM;

    /*
     * 0 - Role client
     * 1 - Role RRE
     * */
    private int USER_ROLE;

    /*
     * 1 - tab 1
     * 2 - tab -2
     * 3 - tab -3
     * 4 - tab -4
     * */

    private int TAB;

    /*
     * CACHE REPO CONSTANTS*/

    private String CACHE_IS_LOGGED = "isLogged";
    private String CACHE_USERNAME = "username";
    private String CACHE_ROLE = "role";
    private String CACHE_TOKEN = "token";
    private String CACHE_TOKEN_TYPE = "token_type";
    private String CACHE_USER_PROFILE_URL = "profile_url";
    private String CACHE_USER_INFO = "user_profile";


//    public static String BASE_URL = "http://192.168.88.62:8092/api/";

    private GetProductModel.Product selectedList;
    private List<GetProductModel.Product> BASE_PRODUCT_LIST;


    /******************************* Getter Setter ******************************************/


    public static String getERROR() {/**/
        return ERROR;
    }

    public static String getEmptyString() {
        return EMPTY_STRING;
    }

    public static String getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    public static String getActivityContext() {
        return ACTIVITY_CONTEXT;
    }

    public static int getDefaultPage() {
        return DEFAULT_PAGE;
    }

    public static int getDefaultPerPage() {
        return DEFAULT_PER_PAGE;
    }

    public static String getAndroidTag() {
        return ANDROID_TAG;
    }

    public int getVIEW_SCREEN_FROM() {
        return VIEW_SCREEN_FROM;
    }

    public void setVIEW_SCREEN_FROM(int VIEW_SCREEN_FROM) {
        this.VIEW_SCREEN_FROM = VIEW_SCREEN_FROM;
    }

    public int getUSER_ROLE() {
        return USER_ROLE;
    }

    public void setUSER_ROLE(int USER_ROLE) {
        this.USER_ROLE = USER_ROLE;
    }

    public int getTAB() {
        return TAB;
    }

    public void setTAB(int TAB) {
        this.TAB = TAB;
    }

    public String getIS_LOGGED() {
        return CACHE_IS_LOGGED;
    }

    public void setIS_LOGGED(String CACHE_IS_LOGGED) {
        this.CACHE_IS_LOGGED = CACHE_IS_LOGGED;
    }

    public String getCACHE_IS_LOGGED() {
        return CACHE_IS_LOGGED;
    }

    public void setCACHE_IS_LOGGED(String CACHE_IS_LOGGED) {
        this.CACHE_IS_LOGGED = CACHE_IS_LOGGED;
    }

    public String getCACHE_USERNAME() {
        return CACHE_USERNAME;
    }

    public void setCACHE_USERNAME(String CACHE_USERNAME) {
        this.CACHE_USERNAME = CACHE_USERNAME;
    }

    public String getCACHE_ROLE() {
        return CACHE_ROLE;
    }

    public void setCACHE_ROLE(String CACHE_ROLE) {
        this.CACHE_ROLE = CACHE_ROLE;
    }

    public String getCACHE_TOKEN() {
        return CACHE_TOKEN;
    }

    public void setCACHE_TOKEN(String CACHE_TOKEN) {
        this.CACHE_TOKEN = CACHE_TOKEN;
    }

    public String getCACHE_TOKEN_TYPE() {
        return CACHE_TOKEN_TYPE;
    }

    public void setCACHE_TOKEN_TYPE(String CACHE_TOKEN_TYPE) {
        this.CACHE_TOKEN_TYPE = CACHE_TOKEN_TYPE;
    }

    public String getCACHE_USER_PROFILE_URL() {
        return CACHE_USER_PROFILE_URL;
    }

    public void setCACHE_USER_PROFILE_URL(String CACHE_USER_PROFILE_URL) {
        this.CACHE_USER_PROFILE_URL = CACHE_USER_PROFILE_URL;
    }

    public String getPasswordPattern() {
        return "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
    }

    public GetProductModel.Product getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(GetProductModel.Product selectedList) {
        this.selectedList = selectedList;
    }

    public List<GetProductModel.Product> getBASE_PRODUCT_LIST() {
        return BASE_PRODUCT_LIST;
    }

    public void setBASE_PRODUCT_LIST(List<GetProductModel.Product> BASE_PRODUCT_LIST) {
        this.BASE_PRODUCT_LIST = BASE_PRODUCT_LIST;
    }

    public String getCACHE_USER_INFO() {
        return CACHE_USER_INFO;
    }

    public void setCACHE_USER_INFO(String CACHE_USER_INFO) {
        this.CACHE_USER_INFO = CACHE_USER_INFO;
    }
}
