package com.register.me.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.view.BaseActivity;
import com.register.me.view.HomeActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.img_tab_one)
    ImageView tabOne;
    @BindView(R.id.img_tab_two)
    ImageView tabTwo;
    @BindView(R.id.img_tab_three)
    ImageView tabThree;
    @BindView(R.id.img_tab_four)
    ImageView tabFour;

    @Inject
    Constants constants;
    private int user_role;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        user_role = constants.getUSER_ROLE();
        if (user_role == 1) {
            tabOne.setImageResource(R.drawable.submission);
            tabTwo.setImageResource(R.drawable.online);
            tabThree.setImageResource(R.drawable.application);
            tabFour.setImageResource(R.drawable.certificate);
        }
    }


    @OnClick(R.id.img_tab_one)
    public void onTabOneClick() {

        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class).putExtra("from", "NEW_PROJECT"));
    }

    @OnClick(R.id.img_tab_two)
    public void onTabTwoClick() {
//        Toast.makeText(this, "New Geography Clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_tab_three)
    public void onTabThreeClick() {
//        Toast.makeText(this, "Renewal Clicked", Toast.LENGTH_SHORT).show();L
    }

    @OnClick(R.id.img_tab_four)
    public void onTabFourClick() {
        if (constants.getUSER_ROLE() == 0) {
            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
        }
    }
}
