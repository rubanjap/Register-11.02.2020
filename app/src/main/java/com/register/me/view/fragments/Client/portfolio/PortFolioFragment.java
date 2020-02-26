package com.register.me.view.fragments.Client.portfolio;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.Adapter.ProductAdapter;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.presenter.PortFolioPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class PortFolioFragment extends BaseFragment implements IFragment, PortFolioPresenter.View, ProductAdapter.OnItemClickListener {

    private static final String FRAGMENT_NAME = "PortFolio";

    @Inject
    PortFolioPresenter portFolioPresenter;
    @Inject
    Constants constants;
    @Inject
    ProductAdapter adapter;
    @BindView(R.id.tv_recycleview)
    RecyclerView recyclerView;
    private List<GetProductModel.Product> list;
    @BindView(R.id.progressbar)
    ConstraintLayout progressBarLayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {

            portFolioPresenter.init(this, getContext());
            portFolioPresenter.getList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setAdapter(List<GetProductModel.Product> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter.init(getContext(), list, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.awarded_prod_dash));
    }



    @OnClick(R.id.tv_add_new_proj)
    public void onNewProjClick() {
        constants.setSelectedList(null);
        if (fragmentChannel != null) {
            fragmentChannel.showAddProduct();
        }
    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_portfolio;
    }

    public static PortFolioFragment newInstance() {
        return new PortFolioFragment();
    }

    @Override
    public void onViewIconClick(int adapterPosition) {
        constants.setVIEW_SCREEN_FROM(0);
        constants.setSelectedList(portFolioPresenter.checkData().get(adapterPosition));
        fragmentChannel.showViewProductDetails();
    }

    @Override
    public void onAuctionIconClick(int adapterPosition) {
        constants.setSelectedList(portFolioPresenter.checkData().get(adapterPosition));
        fragmentChannel.showInitiateProductRegistration();
    }

    @Override
    public void onSendIconClick(int adapterPosition) {
        constants.setSelectedList(portFolioPresenter.checkData().get(adapterPosition));
        fragmentChannel.showDirectAssignment();
    }

    @Override
    public void onCountryIconClick(int adapterPosition) {
        constants.setSelectedList(portFolioPresenter.checkData().get(adapterPosition));
        fragmentChannel.showCountryScreen();
    }

    @Override
    public void onEditIconClick(int adapterPosition) {
        constants.setSelectedList(portFolioPresenter.checkData().get(adapterPosition));
        fragmentChannel.showAddProduct();
    }

    @Override
    public void showErroMessage(String message) {
        KToast.customColorToast(getActivity(), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void updateList(List<GetProductModel.Product> list) {
        this.list = list;
        setAdapter(list);
    }

    @Override
    public void showProgress() {
        if(progressBarLayout.getVisibility()==View.GONE)
        progressBarLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if(progressBarLayout.getVisibility()==View.VISIBLE)
            progressBarLayout.setVisibility(View.GONE);

    }
}
