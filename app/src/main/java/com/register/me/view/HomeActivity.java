package com.register.me.view;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.register.me.R;
import com.register.me.presenter.HomePresenter;
import com.register.me.view.fragmentmanager.FragmentChannel;
import com.register.me.view.fragmentmanager.manager.FragmentManagerHandler;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class HomeActivity extends BaseActivity implements HomePresenter.View, FragmentChannel {

    @Inject
    FragmentManagerHandler fragmentManagerHandler;
    @Inject
    HomePresenter homePresenter;
    @BindView(R.id.activity_home_fl_container)
    FrameLayout flContainer;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.tv_header_home)
    TextView mHeaderText;
    @BindView(R.id.img_back_pressed)
    ImageView mBackPressed;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        injector().inject(this);
        fragmentManagerHandler.setFragmentContainerId(flContainer);

        homePresenter.setView(this);
        if (savedInstanceState == null) {
            homePresenter.init();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentManagerHandler.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        fragmentManagerHandler.onRestoreInstanceState(savedInstanceState);
    }

    @OnClick(R.id.img_nav_click)
    public void onNavClick() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.img_back_pressed)
    public void onBackpressed() {
        fragmentManagerHandler.onBackPressed();
    }

    @Override
    public void fadeOutToolbar() {
      /*  if (tlMain.getAlpha() > 0f) {
            Utils.fadeOut(tlMain);
        }*/
    }

    private void fadeInToolbar() {
        /*if (tlMain.getAlpha() < 1f) {
            Utils.fadeIn(tlMain);
        }*/
    }

    @Override
    public void showHome() {
        fragmentManagerHandler.popUpAll();
        fadeInToolbar();
    }

    @Override
    public void showPortFolio() {
        fragmentManagerHandler.replaceFragment(PortFolioFragment.newInstance(), this);
    }

    @Override
    public void showAddProduct() {
        fragmentManagerHandler.replaceFragment(AddProductFragment.newInstance(), this);
    }

    @Override
    public void showPortFolioDetail() {
//        fragmentManagerHandler.replaceFragment(PortFolioDetailFragment.newInstance(), this);
    }

    @Override
    public void showPortProductDetail() {
//        fragmentManagerHandler.replaceFragment(PortFolioProductDetailFragment.newInstance(), this);
    }

    @Override
    public void showViewProductDetails() {
        fragmentManagerHandler.replaceFragment(ViewProductDetailsFragment.newInstance(), this);
    }

    public void setHeaderText(String header) {
        mBackPressed.setVisibility(View.VISIBLE);
        mHeaderText.setText(header);
    }

    @Override
    public void showDashBoard() {
        fragmentManagerHandler.replaceFragment(DashBoardFragment.newInstance(), this);
    }

    @Override
    public void showActiveProjects() {
        fragmentManagerHandler.replaceFragment(ActiveProjectsFragment.newInstance(), this);
    }

    @Override
    public void showInitiateProductRegistration() {
        fragmentManagerHandler.replaceFragment(InitiateRegistrationFragment.newInstance(), this);
    }

    @Override
    public void showDirectAssignment() {
        fragmentManagerHandler.replaceFragment(CRREDirectFragment.newInstance(),this);
    }

    @Override
    public void showAuctions() {
        fragmentManagerHandler.replaceFragment(AuctionFragment.newInstance(),this);
    }

    @Override
    public void showActiveProjectsSub() {
        fragmentManagerHandler.replaceFragment(ActiveProjectSubFragment.newInstance(),this);
    }

    @Override
    public void showCompleteProject() {
        fragmentManagerHandler.replaceFragment(CompletedProjectFragment.newInstance(),this);
    }

    @Override
    public void setTitle() {

    }

    @Override
    public void dispose() {
        homePresenter.dispose();
    }

    @Override
    public void restore() {

    }

    @Override
    public void onBackPressed() {
        fragmentManagerHandler.onBackPressed();
    }
}