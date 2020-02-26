package com.register.me.presenter;

import android.content.Context;

import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.view.BaseActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

/**
 * Created by Jennifer - AIT on 24-02-2020PM 06:57.
 */
public class CountryPresenter {

    private Context context;
@Inject
    Constants constants;

    public void init(Context context) {
        this.context = context;
        ((BaseActivity)context).injector().inject(this);
    }

    public String getNumber(){
        return constants.getSelectedList().getProduct().getProductNumber();
    }

    public String getName(){
        return constants.getSelectedList().getProduct().getProductName();
    }


    public List<GetProductModel.Project> getCountryList() {
        return constants.getSelectedList().getProject();
    }

}
