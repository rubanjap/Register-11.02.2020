package com.register.me.view.fragments.REA.applicationSubmission;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.register.me.Adapter.ViewPagerAdapter;
import com.register.me.R;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 01:18.
 */
public class ApplicationSubmissionFragment extends BaseFragment implements IFragment {

    @BindView(R.id.tablayoutRRE)
    TabLayout tabLayout;
    @BindView(R.id.viewPagerRRE)
    ViewPager viewPager;
    private final String FRAGMENT_NAME = "RREDashBoard";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout.addTab(tabLayout.newTab().setText("PERSONAL INFO"));
        tabLayout.addTab(tabLayout.newTab().setText("APPLICATION FORM"));
        tabLayout.addTab(tabLayout.newTab().setText("ACTIVITY"));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager(), tabLayout.getTabCount(), 1);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_application_submission;
    }

    public static ApplicationSubmissionFragment newInstance() {
        return new ApplicationSubmissionFragment();
    }

    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.rre_dashboard));
    }
}
