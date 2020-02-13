package com.register.me.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.register.me.R;
import com.register.me.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @OnClick(R.id.card_signIn)
    public void signInClick(){
        startActivity(new Intent(this, WelcomeActivity.class));
        finish();
    }

    @OnClick(R.id.txt_SignUp)
    public void signUpClick(){
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
