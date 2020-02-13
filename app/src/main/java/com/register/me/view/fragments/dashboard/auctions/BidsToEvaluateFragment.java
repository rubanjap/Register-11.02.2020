package com.register.me.view.fragments.dashboard.auctions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.Adapter.AuctionAdapter;
import com.register.me.R;

/**
 * Created by Jennifer - AIT on 11-02-2020PM 06:58.
 */
public class BidsToEvaluateFragment extends Fragment {

    private final AuctionAdapter.OnIconClickListener listener;

    public BidsToEvaluateFragment(AuctionAdapter.OnIconClickListener listener) {
        this.listener=listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bid_evaluation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = view.findViewById(R.id.bid_recyclerView);
        AuctionAdapter auctionAdapter = new AuctionAdapter();
        auctionAdapter.init(getContext(),listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(auctionAdapter);
    }
}
