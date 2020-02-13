package com.register.me.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.view.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.email)
    EditText email;
    @Inject
    Constants constants;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);

    }

    @OnClick(R.id.card_signIn)
    public void signInClick() {

        String emailAddress = email.getText().toString();
        if (emailAddress.equals("0")) {
            startActivity(new Intent(this, WelcomeActivity.class));
            constants.setUSER_ROLE(0);
           finish();
        } else if (emailAddress.equals("1")) {
            startActivity(new Intent(this, WelcomeActivity.class));
            constants.setUSER_ROLE(1);
            finish();
        } else if (emailAddress == null || emailAddress.isEmpty()) {
            Toast.makeText(this, "Please enter valid data", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.txt_SignUp)
    public void signUpClick() {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
