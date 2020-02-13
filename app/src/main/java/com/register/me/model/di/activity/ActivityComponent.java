package com.register.me.model.di.activity;

import com.register.me.presenter.HomePresenter;
import com.register.me.view.HomeActivity;
import com.register.me.view.activity.LoginActivity;
import com.register.me.view.activity.WelcomeActivity;
import com.register.me.view.fragments.dashboardClient.DashBoardFragment;
import com.register.me.view.fragments.dashboardClient.activeProjects.ActiveProjectSubFragment;
import com.register.me.view.fragments.dashboardClient.activeProjects.ActiveProjectsFragment;
import com.register.me.view.fragments.dashboardClient.activeProjects.CompletedProjectFragment;
import com.register.me.view.fragments.dashboardClient.auctions.AuctionFragment;
import com.register.me.view.fragments.dashboardClient.portfolio.PortFolioFragment;
import com.register.me.view.fragments.dashboardClient.portfolio.addProducts.AddProductFragment;
import com.register.me.view.fragments.dashboardClient.portfolio.country.CountryFragment;
import com.register.me.view.fragments.dashboardClient.portfolio.directAssignment.CRREDirectFragment;
import com.register.me.view.fragments.dashboardClient.portfolio.initiateProductRegistration.InitiateRegistrationFragment;
import com.register.me.view.fragments.dashboardClient.portfolio.viewProductDetails.ViewProductDetailsFragment;
import com.register.me.view.fragments.REA.applicationSubmission.ApplicationSubmissionFragment;

import dagger.Subcomponent;


@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(DashBoardFragment dashBoardFragment);

    void inject(WelcomeActivity welcomeActivity);

    void inject(PortFolioFragment portfolioFragment);

    void inject(AddProductFragment addProductFragment);

    void inject(ViewProductDetailsFragment viewProductDetailsFragment);

    void inject(ActiveProjectsFragment activeProjectsFragment);

    void inject(InitiateRegistrationFragment initiateRegistrationFragment);

    void inject(CRREDirectFragment crreDirectFragment);

    void inject(AuctionFragment auctionFragment);

    void inject(ActiveProjectSubFragment activeProjectMenuFragment);

    void inject(CompletedProjectFragment completProjectFragment);

    void inject(CountryFragment countryFragment);

    void inject(ApplicationSubmissionFragment dashBoard_rre_fragment);

    /*Presenter*/
    void inject(HomePresenter homePresenter);

    @Subcomponent.Builder
    interface Builder {
        Builder activityModule(ActivityModule activityModule);

        ActivityComponent build();
    }
}
