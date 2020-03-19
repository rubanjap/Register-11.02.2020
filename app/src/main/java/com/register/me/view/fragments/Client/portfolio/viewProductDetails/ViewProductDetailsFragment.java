package com.register.me.view.fragments.Client.portfolio.viewProductDetails;

/**
 * Created by Jennifer - AIT on 11-02-2020.
 */

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.model.ViewDetails;
import com.register.me.presenter.ViewProductPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class ViewProductDetailsFragment extends BaseFragment implements IFragment, ViewProductPresenter.IViewProduct {

    private static final String FRAGMENT_NAME = "ViewProducts";

    @Inject
    ViewProductPresenter viewProductPresenter;
    @Inject
    Constants constants;
    @BindView(R.id.content)
    LinearLayout contentLayout;
    @BindView(R.id.container)
    LinearLayout container;
    @BindView(R.id.bid_container)
    LinearLayout bidContainer;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.layout_PSubmittedBids)
    ConstraintLayout bidLayout;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.bid_header)
    TextView bidHeader;
    @BindView(R.id.txt_check)
    TextView projectStatus;
    private int screen;
    private ArrayList<KeyValue> map;
    private Resources resources;
    private boolean showBid;
    private List<ViewActCompProject.Comment> comments;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        viewProductPresenter.init(getContext(), this);
        screen = constants.getviewScreenFrom();
        map = new ArrayList<>();
        fragmentChannel.setTitle(getResources().getString(R.string.view_product));


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        resources = Objects.requireNonNull(getContext()).getResources();
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showProgress();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (screen != 3) {
                    viewProductPresenter.triggerApi("");
                } else {
                    viewProductPresenter.triggerApi("ACP");
                }
            }
        },250);

    }

    private void getHeaderList(ArrayList<KeyValue> keyValues) {
        map.clear();
        map.addAll(keyValues);
        setHeaderContainerData(map);
        showBid = viewProductPresenter.hasBids();
        if (showBid) {
            bidLayout.setVisibility(View.VISIBLE);
            setBidContainerData();
        }
    }

    private void setBidContainerData() {
        List<ViewDetails.Crrebiddingdetail> crre = viewProductPresenter.getBidContainerData();
        bidContainer.removeAllViews();
        for (ViewDetails.Crrebiddingdetail crreItem : crre) {
            View inflater = viewProductPresenter.getBidView(crreItem);
            bidContainer.addView(inflater);
        }
    }


    private void setContentContainerData(ArrayList<KeyValue> data) {
        contentLayout.removeAllViews();
        for (KeyValue val : data) {
            View inflater = viewProductPresenter.getContentView(val);
            contentLayout.addView(inflater);
        }

    }


    private void setHeaderContainerData(ArrayList<KeyValue> map) {
        container.removeAllViews();
        for (KeyValue val : map) {
            View view = viewProductPresenter.getHeaderView(val);
            container.addView(view);
        }
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
    public void clickViewProduct() {
        if (contentLayout.getVisibility() == View.VISIBLE) {
            contentLayout.setVisibility(View.GONE);
        } else {
            contentLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.txt_check)
    public void onClickProjectStatus() {

            fragmentChannel.showCommentScreen(comments, viewProductPresenter.getProjectAssignId());
    }

    @Override
    public void buildContent(ArrayList<KeyValue> kv) {
        setContentContainerData(kv);
    }


    @Override
    public void buildHeader(ArrayList<KeyValue> keyValue) {
        scrollView.setVisibility(View.VISIBLE);
        getHeaderList(keyValue);
    }


    @Override
    public void showErroMessage(String message) {
        KToast.customColorToast((Activity) getContext(), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);
    }

    @Override
    public void buildTransactionUI(List<ViewActCompProject.Paymentdetail> paymentdetails) {
        if(paymentdetails.size()!=0) {
            bidLayout.setVisibility(View.VISIBLE);
            bidHeader.setText("Payment Details");
            bidContainer.removeAllViews();
            int i = 1;
            for (ViewActCompProject.Paymentdetail payItem : paymentdetails) {
                View inflater = viewProductPresenter.getBidContainerView(i, payItem);
                bidContainer.addView(inflater);
                i++;
            }
        }
    }


    @Override
    public void displayComments(List<ViewActCompProject.Comment> comments) {
        projectStatus.setVisibility(View.VISIBLE);
            this.comments = comments;
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
    }
}
