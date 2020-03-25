package com.register.me.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.APIs.RRENetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.presenter.WelcomePresenter;
import com.register.me.view.BaseActivity;
import com.register.me.view.HomeActivity;

import java.util.ArrayList;
import java.util.Observable;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.observers.DisposableLambdaObserver;

public class WelcomeActivity extends BaseActivity {

    @BindView(R.id.img_tab_one)
    ImageView tabOne;
    @BindView(R.id.img_tab_two)
    ImageView tabTwo;
    @BindView(R.id.img_tab_three)
    ImageView tabThree;
    @BindView(R.id.img_tab_four)
    ImageView tabFour;
    @BindView(R.id.client_dash)
    LinearLayout clientDash;
    @BindView(R.id.rre_dash)
    LinearLayout mRREDash;

    @Inject
    WelcomePresenter welcomePresenter;
    @Inject
    RRENetworkCall rreNetworkCall;

    @Inject
    Constants constants;
    private Observer<String> message = new Observer<String>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Toast.makeText(WelcomeActivity.this, s, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    };
    private Integer stepFromResponse;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        int userRole = constants.getuserRole();

        welcomePresenter.init(this, message);
        welcomePresenter.getUserProfile();

        if (userRole == 0) {
            clientDash.setVisibility(View.VISIBLE);
        } else {

            Observer<Integer> getStepObserver = new Observer<Integer>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Integer stepsResponse) {
                    stepFromResponse = stepsResponse;
                    setUpRREHome();
                    Toast.makeText(WelcomeActivity.this, stepsResponse.toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable e) {
                    Log.d("Object", String.valueOf(e));

                }

                @Override
                public void onComplete() {

                    Toast.makeText(WelcomeActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                }
            };
            rreNetworkCall.init(this,message);
            rreNetworkCall.getStepStatus(getStepObserver);


        }


    }

    private void setUpRREHome() {
        mRREDash.setVisibility(View.VISIBLE);
        ArrayList<String> title = new ArrayList<>();
        title.add(" Application Submission ");
        title.add(" On - Line Interview ");
        title.add(" Application & Policy Training ");
        title.add(" Certificate ");
        mRREDash.removeAllViews();
        for (int i = 0; i < title.size(); i++) {

            View inflatedView = LayoutInflater.from(this).inflate(R.layout.rre_welcome_item, mRREDash, false);

            ConstraintLayout item = inflatedView.findViewById(R.id.item);
            View topLine = inflatedView.findViewById(R.id.top_line);
            View bottomLine = inflatedView.findViewById(R.id.bottom_line);
            ImageView imageBtn = inflatedView.findViewById(R.id.image_btn);
            TextView txtTitle = inflatedView.findViewById(R.id.title);
            txtTitle.setText(title.get(i));
            int finalI = i;
            item.setOnClickListener(view -> {
                constants.setTAB(finalI + 1);
                startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
            });
      /*    if(stepFromResponse >i+1){
              imageBtn.setImageResource(R.drawable.btn_green);
              topLine.setBackgroundColor(getResources().getColor(R.color.line_color));
              if(stepFromResponse!=i+1){
                  bottomLine.setBackgroundColor(getResources().getColor(R.color.line_color));
              }
          }*/
            if (i == 0) {
                topLine.setVisibility(View.GONE);
            } else if (i == title.size() - 1) {
                bottomLine.setVisibility(View.GONE);
            }
            if (i + 1 < stepFromResponse) {
                imageBtn.setImageResource(R.drawable.btn_green);
            } else if (i + 1 == stepFromResponse) {
                imageBtn.setImageResource(R.drawable.btn_blue);
            }


            mRREDash.addView(inflatedView);
        }
    }


    @OnClick(R.id.img_tab_one)
    public void onTabOneClick() {
        constants.setTAB(1);
        startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));

    }

    @OnClick(R.id.img_tab_two)
    public void onTabTwoClick() {
        constants.setTAB(2);
    }

    @OnClick(R.id.img_tab_three)
    public void onTabThreeClick() {
        constants.setTAB(3);
    }

    @OnClick(R.id.img_tab_four)
    public void onTabFourClick() {
        constants.setTAB(4);
        if (constants.getuserRole() == 0) {
            startActivity(new Intent(WelcomeActivity.this, HomeActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
