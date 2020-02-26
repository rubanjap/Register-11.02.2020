package com.register.me.view;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.navigation.NavigationView;
import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.presenter.HomePresenter;
import com.register.me.view.activity.LoginActivity;
import com.register.me.view.fragmentmanager.FragmentChannel;
import com.register.me.view.fragmentmanager.manager.FragmentManagerHandler;
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
import com.register.me.view.fragments.REA.applicationSubmission.PersonalInfoFragment;
import com.register.me.view.fragments.REA.onlineInterview.OnlineInterviewFragment;
import com.register.me.view.fragments.navigation.ChangePasswordFragment;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;


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
    private String from;

    @BindView(R.id.img_user_profile)
    ImageView userProfile;
    @BindView(R.id.img_user_notification)
    ImageView notification;
    private CircleImageView profileImage;
    private Uri imageUri;
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
        if (getIntent() != null) {
            from = getIntent().getStringExtra("from");
        }
        homePresenter.setView(this);
        if (savedInstanceState == null) {
            homePresenter.init(this, from);
        }
        navigationView.setNavigationItemSelectedListener(this);
        setNavigationHeader();
        updateProfileImage(null);


    }

    private void setNavigationHeader() {
        View hView = navigationView.getHeaderView(0);
        profileImage = (CircleImageView) hView.findViewById(R.id.profile_image);
        TextView profileName = (TextView) hView.findViewById(R.id.txt_profile_name);
        Glide.with(this).load(homePresenter.getProfileImage()).into(profileImage);
        profileName.setText(homePresenter.getUserName());
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileImage.setClickable(false);
                homePresenter.pickImage();
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_change_password:
                showChangePassword();
                break;
            case R.id.nav_logout:
                homePresenter.logout();
                break;
        }
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

                imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                Bitmap converetdImage = homePresenter.getResizedBitmap(bitmap, 400);
                try {

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    converetdImage.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    byte[] byteArray = outputStream.toByteArray();
                    String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
//                Log.d("Encoded", encoded);

                    homePresenter.apiUpdateAvatar(bitmap);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    @OnClick(R.id.img_back_pressed)
    public void onBackpressed() {
       onBackPressed();
//        fragmentManagerHandler.onBackPressed();
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
    public void popUp() {
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
        fadeInToolbar();
    }

    @Override
    public void showChangePassword() {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        fragmentManagerHandler.popUpAll();
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
    public void showClientDashBoard() {
        fragmentManagerHandler.popUpAll();
        fragmentManagerHandler.replaceFragment(DashBoardFragment.newInstance(), this);
    }

    @Override
    public void showNewProject() {
        showAddProduct();
    }

    @Override
    public void showRRE_DashBoard() {
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
    public void logout() {
        startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    @Override
    public void updateProfileImage(String url) {

        if (url == null) {
            url = homePresenter.getProfileImage();
        } else {
            profileImage.setClickable(true);
        }
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
        fragmentManagerHandler.replaceFragment(ActiveProjectSubFragment.newInstance(), this);
    }

    @Override
    public void showCompleteProject() {
        fragmentManagerHandler.replaceFragment(CompletedProjectFragment.newInstance(), this);
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
    public void onBackPressed() {
        if (isProfileClicked) {
            isProfileClicked = false;
        }
        fragmentManagerHandler.onBackPressed();

    }


}
