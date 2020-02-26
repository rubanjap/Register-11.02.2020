package com.register.me.view.fragments.Client.activeProjects;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.Adapter.ProjectAdapter;
import com.register.me.R;
import com.register.me.view.BaseFragment;
import com.register.me.view.fragmentmanager.manager.IFragment;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 12-02-2020PM 12:44.
 */
public class CompletedProjectFragment extends BaseFragment implements IFragment {

    @Inject
    ProjectAdapter adapter;
    @BindView(R.id.active_recycleview)
    RecyclerView complete_recycleview;
    @BindView(R.id.sub_header)
    TextView sub_header;
    @BindView(R.id.txtBid_country)
    TextView txtBid_country;
    public static IFragment newInstance() {
        return new CompletedProjectFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_active_project_sub;
    }

    @Override
    public String getFragmentName() {
        return "ActiveProjectSubFragment";
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        injector().inject(this);
        sub_header.setText("Completed Projects");
        txtBid_country.setText("Country");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        complete_recycleview.setLayoutManager(layoutManager);
        adapter.init(getContext(),1);
        complete_recycleview.setAdapter(adapter);

    }
}
