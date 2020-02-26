package com.register.me.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.register.me.view.fragments.Client.auctions.ActiveAuctionFragment;
import com.register.me.view.fragments.Client.auctions.BidsToEvaluateFragment;
import com.register.me.view.fragments.REA.applicationSubmission.ActivityFragment;
import com.register.me.view.fragments.REA.applicationSubmission.ApplicationFormFragment;
import com.register.me.view.fragments.REA.applicationSubmission.PersonalInfoFragment;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:53.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private AuctionAdapter.OnIconClickListener listener;
    private final int viewCase;
    int totalTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int totalTabs, AuctionAdapter.OnIconClickListener listener, int viewCase) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.totalTabs = totalTabs;
        this.listener = listener;
        this.viewCase = viewCase;
    }

    public ViewPagerAdapter(FragmentManager childFragmentManager, int tabCount, int viewCase) {
        super(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.totalTabs = tabCount;
        this.viewCase = viewCase;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (viewCase) {
            case 0:
                switch (position) {
                    case 0:
                        return new ActiveAuctionFragment(listener);
                    case 1:
                        return new BidsToEvaluateFragment(listener);
                    default:
                        return null;
                }
            case 1:
                switch (position) {
                    case 0:
                        return new PersonalInfoFragment();
                    case 1:
                        return new ApplicationFormFragment();
                    case 2:
                        return new ActivityFragment();
                    default:
                        return null;
                }
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
