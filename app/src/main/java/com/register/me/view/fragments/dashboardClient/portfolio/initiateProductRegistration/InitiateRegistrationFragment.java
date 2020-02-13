package com.register.me.view.fragments.dashboardClient.portfolio.initiateProductRegistration;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.register.me.R;
import com.register.me.model.data.util.Util;
import com.register.me.presenter.InitiateRegistrationPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */
public class InitiateRegistrationFragment extends BaseFragment implements IFragment, InitiateRegistrationPresenter.View, Util.UtilInterface {
    private static final String FRAGMENT_NAME = "InitiateProductRegistration";

    @BindView(R.id.radio_date)
    CheckBox date;
    @BindView(R.id.date)
    TextView txtDate;


    @Inject
    InitiateRegistrationPresenter initiateRegistrationPresenter;
    @Inject
    Util util;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        initiateRegistrationPresenter.setView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    util.showCalendar(getContext(),InitiateRegistrationFragment.this::onDateSet);
                }else {
                    if (txtDate.getVisibility()== View.VISIBLE) {
                        txtDate.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.product_registration));
    }

    @Override
    public void dispose() {
        initiateRegistrationPresenter.dispose();
    }

    @Override
    public void restore() {

    }

    @Override
    public void setTitle() {

    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_initiate_product;
    }

    public static InitiateRegistrationFragment newInstance() {
        return new InitiateRegistrationFragment();
    }


    @Override
    public void onDateSet(long timeInMillis) {
        txtDate.setVisibility(View.VISIBLE);
        DateFormat formatter = new SimpleDateFormat("MMMM dd,yyyy");
        txtDate.setText(formatter.format(timeInMillis));
    }
}
