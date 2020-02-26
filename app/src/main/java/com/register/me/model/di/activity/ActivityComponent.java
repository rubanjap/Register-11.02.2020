package com.register.me.model.di.activity;

import com.register.me.presenter.AddProductPresenter;
import com.register.me.presenter.ChangePasswordPresenter;
import com.register.me.presenter.CountryPresenter;
import com.register.me.presenter.HomePresenter;
import com.register.me.presenter.LoginPresenter;
import com.register.me.presenter.PersonalInfoPresenter;
import com.register.me.presenter.PortFolioPresenter;
import com.register.me.presenter.SignUpPresenter;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.presenter.WelcomePresenter;
import com.register.me.view.HomeActivity;
import com.register.me.view.activity.LoginActivity;
import com.register.me.view.activity.SignUpActivity;
import com.register.me.view.activity.WelcomeActivity;
import com.register.me.view.fragments.REA.applicationSubmission.PersonalInfoFragment;
import com.register.me.view.fragments.REA.onlineInterview.OnlineInterviewFragment;
import com.register.me.view.fragments.Client.DashBoardFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectSubFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectsFragment;
import com.register.me.view.fragments.Client.activeProjects.CompletedProjectFragment;
import com.register.me.view.fragments.Client.auctions.AuctionFragment;
import com.register.me.view.fragments.Client.portfolio.PortFolioFragment;
import com.register.me.view.fragments.Client.portfolio.addProducts.AddProductFragment;
import com.register.me.view.fragments.Client.portfolio.country.CountryFragment;
import com.register.me.view.fragments.Client.portfolio.directAssignment.CRREDirectFragment;
import com.register.me.view.fragments.Client.portfolio.initiateProductRegistration.InitiateRegistrationFragment;
import com.register.me.view.fragments.Client.portfolio.viewProductDetails.ViewProductDetailsFragment;
import com.register.me.view.fragments.REA.applicationSubmission.ApplicationSubmissionFragment;
import com.register.me.view.fragments.navigation.ChangePasswordFragment;

import dagger.Subcomponent;


@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {

    void inject(HomeActivity homeActivity);

    void inject(LoginActivity loginActivity);

    void inject(SignUpActivity signUpActivity);

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

    void inject(OnlineInterviewFragment onlineInterviewFragment);

    void inject(ChangePasswordFragment changePasswordFragment);

    void inject(PersonalInfoFragment personalInfoFragment);


    /*Presenter*/
    void inject(LoginPresenter loginPresenter);

    void inject(SignUpPresenter signUpPresenter);

    void inject(HomePresenter homePresenter);

    void inject(ChangePasswordPresenter changePasswordPresenter);

    void inject(PersonalInfoPresenter personalInfoPresenter);

    void inject(PortFolioPresenter portFolioPresenter);

    void inject(AddProductPresenter addProductPresenter);

    void inject(ViewProductPresenter viewProductPresenter);

    void inject(CountryPresenter countryPresenter);

    void inject(WelcomePresenter welcomePresenter);

    @Subcomponent.Builder
    interface Builder {
        Builder activityModule(ActivityModule activityModule);

        ActivityComponent build();
    }
}
