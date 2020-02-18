package com.register.me.view.fragments.REA.onlineInterview;

import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.register.me.R;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.text.SimpleDateFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 14-02-2020PM 01:51.
 */
public class OnlineInterviewFragment extends BaseFragment implements IFragment, Utils.UtilDateTimeInterface {

    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtTime)
    TextView txtTime;

    @Inject
    Utils utils;

    public static IFragment newInstance() {
        return new OnlineInterviewFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_online_interview;
    }

    @Override
    public String getFragmentName() {
        return "OnlineInterview";
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.layout_date)
    public void onClickDate() {
        utils.showCalendar(getContext(), this, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick(R.id.layout_time)
    public void onClickTime() {
        utils.showCalendar(getContext(), this, 1);
    }

    @Override
    public void onDateSet(long timeInMillis) {
        txtDate.setText(new SimpleDateFormat("dd-MM-yyyy").format(timeInMillis));
    }

    @Override
    public void onTimeSet(Integer currentHour, Integer currentMinute) {
        if (currentHour > 12) {
            txtTime.setText("" + (currentHour-12) + " : " + currentMinute+" "+"P.M");
        } else {
            txtTime.setText("" + currentHour + " : " + currentMinute+" "+"A.M");
        }
    }
}
