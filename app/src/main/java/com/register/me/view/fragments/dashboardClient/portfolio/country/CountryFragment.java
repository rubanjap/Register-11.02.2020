package com.register.me.view.fragments.dashboardClient.portfolio.country;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import butterknife.BindView;


public class CountryFragment extends BaseFragment implements IFragment {

    private static final String FRAGMENT_NAME = "Country";

    @BindView(R.id.country_container)
    LinearLayout container;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        container.removeAllViews();
for(int i =0; i<10;i++){
    View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.country_item, null, false);
    container.addView(inflatedView);

}
    }



    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.country));
    }




    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_country_list;
    }

    public static CountryFragment newInstance() {
        return new CountryFragment();
    }

}
