package com.register.me.model.data;


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
    public int VIEW_SCREEN_FROM;

    /*
     * 0 - Role client
     * 1 - Role RRE
     * */
    public int USER_ROLE;

    /*
     * 1 - tab 1
     * 2 - tab -2
     * 3 - tab -3
     * 4 - tab -4
     * */

    public int TAB;

    /*
     * CACHE REPO CONSTANTS*/

    public String CACHE_IS_LOGGED = "isLogged";
    public String CACHE_USERNAME = "username";
    public String CACHE_ROLE = "role";
    public String CACHE_TOKEN = "token";
    public String CACHE_TOKEN_TYPE = "token_type";
    public String CACHE_USER_PROFILE_URL = "profile_url";


    public static String BASE_URL = "http://3.81.189.43/api/";


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

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
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
}
