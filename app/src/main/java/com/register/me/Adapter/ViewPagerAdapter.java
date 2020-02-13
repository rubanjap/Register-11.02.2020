package com.register.me.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.register.me.view.fragments.dashboard.auctions.ActiveAuctionFragment;
import com.register.me.view.fragments.dashboard.auctions.BidsToEvaluateFragment;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:53.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final AuctionAdapter.OnIconClickListener listener;
    int totalTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int totalTabs, AuctionAdapter.OnIconClickListener listener) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.totalTabs = totalTabs;
        this.listener = listener;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new ActiveAuctionFragment(listener);
            case 1:
                return new BidsToEvaluateFragment(listener);

            default:

                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}
