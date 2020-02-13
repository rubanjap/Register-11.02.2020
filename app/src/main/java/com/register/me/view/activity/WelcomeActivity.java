package com.register.me.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.register.me.R;
import com.register.me.view.BaseActivity;
import com.register.me.view.HomeActivity;

import butterknife.OnClick;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }


    @OnClick(R.id.img_new_proj)
    public void onProjClick(){

        Toast.makeText(this, "New Project Clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_new_geo)
    public void onGeoClick(){
        Toast.makeText(this, "New Geography Clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_renewal)
    public void onRenewalClick(){
        Toast.makeText(this, "Renewal Clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_my_board)
    public void onDashBoardClick(){
        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
    }
}
