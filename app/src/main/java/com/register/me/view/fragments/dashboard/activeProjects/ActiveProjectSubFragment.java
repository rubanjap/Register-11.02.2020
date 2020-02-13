package com.register.me.view.fragments.dashboard.activeProjects;

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
public class ActiveProjectSubFragment extends BaseFragment implements IFragment {

    @Inject
    ProjectAdapter adapter;
    @BindView(R.id.active_recycleview)
    RecyclerView active_recycleview;
    @BindView(R.id.sub_header)
    TextView sub_header;

    public static IFragment newInstance() {
        return new ActiveProjectSubFragment();
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

        sub_header.setText("Active Projects");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        active_recycleview.setLayoutManager(layoutManager);
        adapter.init(getContext(),0);
        active_recycleview.setAdapter(adapter);

    }
}
