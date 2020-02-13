package com.register.me.view.fragments.addProducts;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.presenter.AddProductPresenter;
import com.register.me.presenter.DashBoardPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by Jennifer - AIT.
 */
public class AddProductFragment extends BaseFragment implements IFragment, AddProductPresenter.View {

    private static final String FRAGMENT_NAME = "DashBoard";

    @Inject AddProductPresenter addProductPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        addProductPresenter.setView(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity)getActivity()).setHeaderText(getResources().getString(R.string.client_dashboard));
    }

    @Override
    public void dispose() {
        addProductPresenter.dispose();
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
        return R.layout.fragment_add_product;
    }

    public static AddProductFragment newInstance() {
        return new AddProductFragment();
    }

    @OnClick(R.id.add_product)
    public void addProducct(){
        fragmentChannel.showViewProductDetails();
    }
}
