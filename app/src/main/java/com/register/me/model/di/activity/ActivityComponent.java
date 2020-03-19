package com.register.me.model.di.activity;

import com.register.me.presenter.ActiveAuctionPresenter;
import com.register.me.presenter.ActiveCompProjectPresenter;
import com.register.me.presenter.AddProductPresenter;
import com.register.me.presenter.ChangePasswordPresenter;
import com.register.me.presenter.CommentPresenter;
import com.register.me.presenter.CountryPresenter;
import com.register.me.presenter.HomePresenter;
import com.register.me.presenter.InitiateRegistrationPresenter;
import com.register.me.presenter.LoginPresenter;
import com.register.me.presenter.PersonalInfoPresenter;
import com.register.me.presenter.PortFolioPresenter;
import com.register.me.presenter.ProjectAssignPresenter;
import com.register.me.presenter.SignUpPresenter;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.presenter.WelcomePresenter;
import com.register.me.view.HomeActivity;
import com.register.me.view.activity.LoginActivity;
import com.register.me.view.activity.SignUpActivity;
import com.register.me.view.activity.WelcomeActivity;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectCompFragment;
import com.register.me.view.fragments.Client.activeProjects.CommentFragment;
import com.register.me.view.fragments.Client.activeProjects.ProjectAssignFragment;
import com.register.me.view.fragments.Client.auctions.ActiveAuctionFragment;
import com.register.me.view.fragments.Client.auctions.BidsToEvaluateFragment;
import com.register.me.view.fragments.REA.applicationSubmission.PersonalInfoFragment;
import com.register.me.view.fragments.REA.onlineInterview.OnlineInterviewFragment;
import com.register.me.view.fragments.Client.DashBoardFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectsFragment;
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

    void inject(ActiveProjectCompFragment activeProjectMenuFragment);

    void inject(CountryFragment countryFragment);

    void inject(ApplicationSubmissionFragment submissionFragment);

    void inject(OnlineInterviewFragment onlineInterviewFragment);

    void inject(ChangePasswordFragment changePasswordFragment);

    void inject(PersonalInfoFragment personalInfoFragment);

    void inject(ActiveAuctionFragment activeAuctionFragment);

    void inject(BidsToEvaluateFragment bidsToEvaluateFragment);

    void inject(ProjectAssignFragment projectAssignFragment);

    void inject(CommentFragment commentFragment);



    void inject(WelcomeRREActivity welcomeRREActivity);







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

    void inject(InitiateRegistrationPresenter registrationPresenter);

    void inject(ActiveAuctionPresenter activeAuctionPresenter);

    void inject(ActiveCompProjectPresenter activeCompProjectPresenter);

    void inject(ProjectAssignPresenter projectAssignPresenter);

    void inject(CommentPresenter commentPresenter);




    @Subcomponent.Builder
    interface Builder {
        Builder activityModule(ActivityModule activityModule);

        ActivityComponent build();
    }
}
