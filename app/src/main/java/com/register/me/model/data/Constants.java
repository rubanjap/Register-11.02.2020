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

    /******************************* Getter Setter ******************************************/

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
}
