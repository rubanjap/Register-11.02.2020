package com.register.me.model.di.activity;

import com.register.me.view.HomeActivity;
import com.register.me.view.activity.WelcomeActivity;
import com.register.me.view.fragments.dashboard.activeProjects.ActiveProjectSubFragment;
import com.register.me.view.fragments.dashboard.activeProjects.ActiveProjectsFragment;
import com.register.me.view.fragments.dashboard.activeProjects.CompletedProjectFragment;
import com.register.me.view.fragments.addProducts.AddProductFragment;
import com.register.me.view.fragments.addProducts.directAssignment.CRREDirectFragment;
import com.register.me.view.fragments.addProducts.initiateProductRegistration.InitiateRegistrationFragment;
import com.register.me.view.fragments.addProducts.viewProductDetails.ViewProductDetailsFragment;
import com.register.me.view.fragments.dashboard.auctions.AuctionFragment;
import com.register.me.view.fragments.dashboard.DashBoardFragment;
import com.register.me.view.fragments.dashboard.portfolio.PortFolioFragment;
import dagger.Subcomponent;


@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

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


    @Subcomponent.Builder
    interface Builder {
        Builder activityModule(ActivityModule activityModule);

        ActivityComponent build();
    }
}
