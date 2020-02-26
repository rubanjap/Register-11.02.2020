package com.register.me.view.fragments.Client.portfolio.country;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.presenter.CountryPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;


public class CountryFragment extends BaseFragment implements IFragment {

    private static final String FRAGMENT_NAME = "Country";

    @BindView(R.id.country_container)
    LinearLayout container;

    @BindView(R.id.pNumber)
    TextView number;
    @BindView(R.id.pName)
    TextView name;
    @Inject
    CountryPresenter presenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        number.setText(presenter.getNumber());
        name.setText(presenter.getName());
        container.removeAllViews();
        List<GetProductModel.Project> cList = presenter.getCountryList();
        for (int i = 0; i < cList.size(); i++) {
            View inflatedView = LayoutInflater.from(getContext()).inflate(R.layout.country_item, null, false);
            TextView country = inflatedView.findViewById(R.id.txt_country);
            TextView startDate = inflatedView.findViewById(R.id.txt_startDate);
            TextView status = inflatedView.findViewById(R.id.txt_status);
            ImageView viewIcon = inflatedView.findViewById(R.id.view);
            ImageView directAssignIcon = inflatedView.findViewById(R.id.direct_assign);
            ImageView closeIcon = inflatedView.findViewById(R.id.close);
            GetProductModel.Project project = cList.get(i);
            country.setText(project.getProjectLocation().getRegion());
            String bidStartDate = project.getProductOppurtunity().getBidStartDate();
            startDate.setText(bidStartDate);
            status.setText(project.getBidstatus());
            viewIcon.setVisibility(project.getIsView() ? View.VISIBLE : View.GONE);
            directAssignIcon.setVisibility(project.getIsdirectassignment() ? View.VISIBLE : View.GONE);
            closeIcon.setVisibility(project.getIsCancel() ? View.VISIBLE : View.GONE);
            viewIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentChannel.showViewProductDetails();
                }
            });
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
