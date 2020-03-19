package com.register.me.model.data;


import com.register.me.model.data.model.ActiveAuction;
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
     * 1 - Screen from Project Portfolio
     * 2 - Screen from Country
     * */
    private int VIEW_SCREEN_FROM;

    /*
     * 0 - Role client
     * 1 - Role RRE
     * 2 - Role CRRE
     * 3-
     * */
    private int USER_ROLE;

    /*
     * 1 - tab 1
     * 2 - tab -2
     * 3 - tab -3
     * 4 - tab -4
     * */

    private int TAB;

    private String productID;

    private String projectID;

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
    List<ActiveAuction.Bidsreadytoevaluate_> BidList;

    private boolean isAcitiveProject;


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

    public int getviewScreenFrom() {
        return VIEW_SCREEN_FROM;
    }

    public void setviewScreenFrom(int VIEW_SCREEN_FROM) {
        this.VIEW_SCREEN_FROM = VIEW_SCREEN_FROM;
    }

    public int getuserRole() {
        return USER_ROLE;
    }

    public void setuserRole(int USER_ROLE) {
        this.USER_ROLE = USER_ROLE;
    }

    public int getTAB() {
        return TAB;
    }

    public void setTAB(int TAB) {
        this.TAB = TAB;
    }


    public String getcacheIsLoggedKey() {
        return CACHE_IS_LOGGED;
    }


    public String getcacheUsernameKey() {
        return CACHE_USERNAME;
    }


    public String getcacheRoleKey() {
        return CACHE_ROLE;
    }


    public String getcacheTokenKey() {
        return CACHE_TOKEN;
    }


    public String getcacheTokenTypeKey() {
        return CACHE_TOKEN_TYPE;
    }


    public String getcacheUserProfileUrlKey() {
        return CACHE_USER_PROFILE_URL;
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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public List<ActiveAuction.Bidsreadytoevaluate_> getBidList() {
        return BidList;
    }

    public void setBidList(List<ActiveAuction.Bidsreadytoevaluate_> bidList) {
        BidList = bidList;
    }

    public boolean isAcitiveProject() {
        return isAcitiveProject;
    }

    public void setAcitiveProject(boolean acitiveProject) {
        isAcitiveProject = acitiveProject;
    }
}
