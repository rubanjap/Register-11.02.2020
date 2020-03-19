package com.register.me.view.fragmentmanager;


import com.register.me.model.data.model.ViewActCompProject;

import java.util.List;

public interface FragmentChannel {

    void popUp();

    void popUpAll();

    void updateNavigation();

    void showHome();

    void setTitle(String title);

    void showChangePassword();

    void showPortFolio();

    void showAddProduct();

    void showViewProductDetails();

    void showActiveProjects();

    void showInitiateProductRegistration();

    void showDirectAssignment();

    void showAuctions();

    void showActiveProjectsSub();

    void showCountryScreen();

    void showRREDashboard();

    void showOnlineInterView();

    void showPersonalInfo();

    void showProjectAssign(String name, int locationid, String region);

    void showCommentScreen(List<ViewActCompProject.Comment> comments, int projectAssignId);
}
