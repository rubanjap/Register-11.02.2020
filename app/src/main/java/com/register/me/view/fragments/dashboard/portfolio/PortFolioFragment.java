package com.register.me.view.fragments.dashboard.portfolio;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.Adapter.ProductAdapter;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.presenter.PortFolioPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class PortFolioFragment extends BaseFragment implements IFragment, PortFolioPresenter.View, ProductAdapter.OnItemClickListener {

    private static final String FRAGMENT_NAME = "PortFolio";

    @Inject PortFolioPresenter portFolioPresenter;
    @Inject
    Constants constants;
    @Inject
    ProductAdapter adapter;
    @BindView(R.id.tv_recycleview)
    RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        portFolioPresenter.setView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        adapter.init(getContext(), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).setHeaderText(getResources().getString(R.string.awarded_prod_dash));
    }

    @Override
    public void dispose() {
        portFolioPresenter.dispose();
    }

    @Override
    public void restore() {

    }

    @Override
    public void setTitle() {

    }

    @OnClick(R.id.tv_add_new_proj)
    public void onNewProjClick(){
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
        fragmentChannel.showViewProductDetails();
    }

    @Override
    public void onAuctionIconClick(int adapterPosition) {
        fragmentChannel.showInitiateProductRegistration();
    }

    @Override
    public void onSendIconClick(int adapterPosition) {
        fragmentChannel.showDirectAssignment();
    }
}
