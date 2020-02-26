package com.register.me.view.fragments.Client.portfolio.viewProductDetails;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.KeyValue;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class ViewProductDetailsFragment extends BaseFragment implements IFragment {

    private static final String FRAGMENT_NAME = "ViewProducts";

    @Inject
    ViewProductPresenter viewProductPresenter;
    @Inject
    Constants constants;
    @BindView(R.id.content)
    LinearLayout contentLayout;
    @BindView(R.id.container)
    LinearLayout container;
    private int screen;
    private ArrayList<KeyValue> map;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        viewProductPresenter.init(getContext());
        screen = constants.getVIEW_SCREEN_FROM();
        map = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<KeyValue> kv = viewProductPresenter.extractData();
        container.removeAllViews();
        for (KeyValue val : kv) {
            View inflater = LayoutInflater.from(getContext()).inflate(R.layout.item_view_details, null, false);
            TextView title = inflater.findViewById(R.id.txtTitle);
            TextView content = inflater.findViewById(R.id.txtContent);
            title.setText(val.getKey());
            if (val.getValue() != null) {
                String boolVal = "";
                boolVal = getBoolValue(val);
                content.setText(boolVal);
            } else {
                StringBuilder builder = getStringFromList(val);
                if(builder.toString().isEmpty()){
                    content.setText("-");
                }else {
                content.setText(builder.toString());}
            }

            contentLayout.addView(inflater);
        }


        GetProductModel.Product list = constants.getSelectedList();
        switch (screen) {
            case 0:
                map.clear();
                map.add(new KeyValue(getContext().getResources().getString(R.string.product_number), list.getProduct().getProductNumber(),null));
                map.add(new KeyValue(getContext().getResources().getString(R.string.product_name), list.getProduct().getProductName(),null));
                break;
            case 1:
                map.clear();
                map.add(new KeyValue("Product Number", "HHS412SN",null));
                map.add(new KeyValue("Country", "Brazil",null));
                map.add(new KeyValue("Available to Bid", "4",null));
                map.add(new KeyValue("Have Submitted Bid", "1",null));
                map.add(new KeyValue("Bid Status", "Project Assigned",null));
                break;
            case 2:
                map.clear();
                map.add(new KeyValue("Product Number", "HHS412SN",null));
                map.add(new KeyValue("Country", "Brazil",null));
                map.add(new KeyValue("Available to Bid", "4",null));
                map.add(new KeyValue("Have Submitted Bid", "1",null));
                map.add(new KeyValue("Bid Status", "Project Assigned",null));
                break;
        }

        setContainerData();
    }

    @NotNull
    private StringBuilder getStringFromList(KeyValue val) {
        List<String> subList = val.getSubList();
        StringBuilder builder = new StringBuilder();
        if ((subList != null) && (subList.size() > 0)) {
            Iterator iterator = subList.iterator();
            while (iterator.hasNext()) {
                if (builder.toString().equals("")) {
                    builder.append(iterator.next());
                } else {
                    builder.append("\n").append(iterator.next().toString().trim());
                }
            }

        }
        return builder;
    }

    private String getBoolValue(KeyValue val) {
        String mYES = "YES";
        String mNO = "NO";
        switch (val.getValue()) {
            case "null":
                return "-";
            case "true":
                return mYES;
            case "false":
                return mNO;
            default:
                return val.getValue();
        }
    }

    private void setContainerData() {
        container.removeAllViews();
        for (KeyValue val : map) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.key_value_item, null, false);
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
