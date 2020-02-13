package com.register.me.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.register.me.R;
import com.register.me.view.BaseActivity;

import butterknife.OnClick;

public class SignUpActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_up;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.txtSignIn)
    public void onSignIn(){
        finish();
    }
}
