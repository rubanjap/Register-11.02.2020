package com.register.me.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.presenter.HomePresenter;
import com.register.me.view.activity.LoginActivity;
import com.register.me.view.fragmentmanager.FragmentChannel;
import com.register.me.view.fragmentmanager.manager.FragmentManagerHandler;
import com.register.me.view.fragments.Client.DashBoardFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectCompFragment;
import com.register.me.view.fragments.Client.activeProjects.ActiveProjectsFragment;
import com.register.me.view.fragments.Client.activeProjects.CommentFragment;
import com.register.me.view.fragments.Client.activeProjects.ProjectAssignFragment;
import com.register.me.view.fragments.Client.auctions.AuctionFragment;
import com.register.me.view.fragments.Client.portfolio.PortFolioFragment;
import com.register.me.view.fragments.Client.portfolio.addProducts.AddProductFragment;
import com.register.me.view.fragments.Client.portfolio.country.CountryFragment;
import com.register.me.view.fragments.Client.portfolio.directAssignment.CRREDirectFragment;
import com.register.me.view.fragments.Client.portfolio.initiateProductRegistration.InitiateRegistrationFragment;
import com.register.me.view.fragments.Client.portfolio.viewProductDetails.ViewProductDetailsFragment;
import com.register.me.view.fragments.REA.applicationSubmission.ApplicationSubmissionFragment;
import com.register.me.view.fragments.REA.applicationSubmission.PersonalInfoFragment;
import com.register.me.view.fragments.REA.onlineInterview.OnlineInterviewFragment;
import com.register.me.view.fragments.navigation.ChangePasswordFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.view.Gravity.LEFT;


public class HomeActivity extends BaseActivity implements HomePresenter.View, FragmentChannel, NavigationView.OnNavigationItemSelectedListener {

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
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.img_user_profile)
    ImageView userProfile;
    @BindView(R.id.img_user_notification)
    ImageView notification;
    private CircleImageView profileImage;
    private boolean isProfileClicked = false;

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
            homePresenter.init(this);
        }
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationHeader();
        updateProfileImage(null);

    }

    private void setNavigationHeader() {
        View hView = navigationView.getHeaderView(0);
        profileImage = (CircleImageView) hView.findViewById(R.id.profile_image);
        TextView profileName = (TextView) hView.findViewById(R.id.txt_profile_name);
        String profileImage = homePresenter.getProfileImage();
        if(profileImage!=null){
        Glide.with(this).load(profileImage).into(this.profileImage);}

        profileName.setText(homePresenter.getUserName());
        this.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity.this.profileImage.setClickable(false);
                homePresenter.pickImage();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                finish();
                break;
            case R.id.nav_change_password:
                showChangePassword();
                break;
            case R.id.nav_logout:
                homePresenter.logout();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + id);
        }
        mDrawerLayout.closeDrawer(LEFT);
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            homePresenter.pickImage();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK &&
                    data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                homePresenter.apiUpdateAvatar(bitmap);
                bitmap.recycle();
            }
        } catch (Exception e) {
            Log.e("Exception", e.getMessage());
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


    @OnClick(R.id.img_user_profile)
    public void onClickUserProfile() {
        if (!isProfileClicked) {
            isProfileClicked = true;
            showPersonalInfo();
        }
    }

    @OnClick(R.id.img_user_notification)
    public void onClickNotification() {
        Toast.makeText(this, "Notification Clicked", Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.img_nav_click)
    public void onNavClick() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @OnClick(R.id.img_back_pressed)
    public void onClickBackpressed() {
        onBackPressed();
    }


    @Override
    public void popUp() {
        if(fragmentManagerHandler.getFragmentName().equals("PersonalInfo")){
            isProfileClicked=false;
        }
        fragmentManagerHandler.popUp();
    }

    @Override
    public void popUpAll() {
        fragmentManagerHandler.popUpAll();
    }

    @Override
    public void updateNavigation() {
        setNavigationHeader();
    }

    @Override
    public void showHome() {
        fragmentManagerHandler.popUpAll();
        showClientDashBoard();
    }

    @Override
    public void setTitle(String title) {
        mBackPressed.setVisibility(View.VISIBLE);
        mHeaderText.setText(title.toUpperCase());
    }

    @Override
    public void showChangePassword() {
        fragmentManagerHandler.replaceFragment(ChangePasswordFragment.newInstance(), this);
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
    public void showViewProductDetails() {
        fragmentManagerHandler.replaceFragment(ViewProductDetailsFragment.newInstance(), this);
    }

    @Override
    public void showClientDashBoard() {
        fragmentManagerHandler.popUpAll();
        fragmentManagerHandler.replaceFragment(DashBoardFragment.newInstance(), this);
    }

    @Override
    public void showNewProject() {
        showAddProduct();
    }

    @Override
    public void showRREDashBoard() {
        showRREDashboard();
    }

    @Override
    public void showOnlineInter() {
        showOnlineInterView();
    }

    @Override
    public void showErrorMessage(String message) {
        KToast.customColorToast(this, message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }


    @Override
    public void updateProfileImage(String url) {

        if (url == null) {
            url = homePresenter.getProfileImage();
        }
            profileImage.setClickable(true);

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true);

        Glide.with(this)
                .applyDefaultRequestOptions(options)
                .load(url)
                .into(profileImage);
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
        fragmentManagerHandler.replaceFragment(CRREDirectFragment.newInstance(), this);
    }

    @Override
    public void showAuctions() {
        fragmentManagerHandler.replaceFragment(AuctionFragment.newInstance(), this);
    }

    @Override
    public void showActiveProjectsSub() {
        fragmentManagerHandler.replaceFragment(ActiveProjectCompFragment.newInstance(), this);
    }

    @Override
    public void showCountryScreen() {
        fragmentManagerHandler.replaceFragment(CountryFragment.newInstance(), this);
    }

    @Override
    public void showRREDashboard() {
        fragmentManagerHandler.replaceFragment(ApplicationSubmissionFragment.newInstance(), this);
    }

    @Override
    public void showOnlineInterView() {
        fragmentManagerHandler.replaceFragment(OnlineInterviewFragment.newInstance(), this);
    }

    @Override
    public void showPersonalInfo() {
        fragmentManagerHandler.replaceFragment(PersonalInfoFragment.newInstance(), this);
    }

    @Override
    public void showProjectAssign(String name, int locationid, String region) {
        fragmentManagerHandler.replaceFragment(ProjectAssignFragment.newInstance(name, locationid,region),this);
    }

    @Override
    public void showCommentScreen(List<ViewActCompProject.Comment> comments, int projectAssignId) {
        fragmentManagerHandler.replaceFragment(CommentFragment.newInstance(comments,projectAssignId),this);
    }

    @Override
    public void onBackPressed() {
        if (isProfileClicked) {
            isProfileClicked = false;
        }
        if(fragmentManagerHandler.getStack()>0) {
            fragmentManagerHandler.onBackPressed();
        }else {
            super.onBackPressed();
        }
    }

}
