package com.register.me.view.fragments.dashboardClient.portfolio.viewProductDetails;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.api.model.KeyValue;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class ViewProductDetailsFragment extends BaseFragment implements IFragment, ViewProductPresenter.View {

    private static final String FRAGMENT_NAME = "ViewProducts";

    @Inject
    ViewProductPresenter viewProductPresenter;
    @Inject
    Constants constants;
    @BindView(R.id.content)
    ConstraintLayout contentLayout;
    @BindView(R.id.container)
    LinearLayout container;
    private int screen;
    private ArrayList<KeyValue> map;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        viewProductPresenter.setView(this);
        screen = constants.getVIEW_SCREEN_FROM();
        map = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switch (screen) {
            case 0:
                map.clear();
                map.add(new KeyValue("Product Number", "HHS412SN"));
                map.add(new KeyValue("Product Name", "HAMILTON HEATER SHAKER"));
                break;
            case 1:
                map.clear();
                map.add(new KeyValue("Product Number", "HHS412SN"));
                map.add(new KeyValue("Country", "Brazil"));
                map.add(new KeyValue("Available to Bid", "4"));
                map.add(new KeyValue("Have Submitted Bid", "1"));
                map.add(new KeyValue("Bid Status", "Project Assigned"));
                break;
            case 2:
                map.clear();
                map.add(new KeyValue("Product Number", "HHS412SN"));
                map.add(new KeyValue("Country", "Brazil"));
                map.add(new KeyValue("Available to Bid", "4"));
                map.add(new KeyValue("Have Submitted Bid", "1"));
                map.add(new KeyValue("Bid Status", "Project Assigned"));
                break;
        }

        setContainerData();
    }

    private void setContainerData() {
    container.removeAllViews();
    for(KeyValue val : map){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.key_value_item,null,false);
        TextView key = view.findViewById(R.id.txtKey);
        TextView value = view.findViewById(R.id.txtValue);
        key.setText(val.getKey());
        value.setText(val.getValue());
        container.addView(view);
    }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.view_product));
    }

    @Override
    public void dispose() {
        viewProductPresenter.dispose();
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
        return R.layout.fragment_view_product;
    }

    public static ViewProductDetailsFragment newInstance() {
        return new ViewProductDetailsFragment();
    }

    @OnClick(R.id.viewProduct)
    public void ClickViewProduct() {
        if (contentLayout.getVisibility() == View.VISIBLE) {
            contentLayout.setVisibility(View.GONE);
        } else {
            contentLayout.setVisibility(View.VISIBLE);
        }
    }
}
