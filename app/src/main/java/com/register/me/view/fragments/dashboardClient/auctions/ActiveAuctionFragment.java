package com.register.me.view.fragments.dashboardClient.auctions;

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
public class ActiveAuctionFragment extends Fragment{


    private final AuctionAdapter.OnIconClickListener listener;

    public ActiveAuctionFragment(AuctionAdapter.OnIconClickListener listener) {
        this.listener=listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_active_auctions, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.active_recycleview);
        AuctionAdapter auctionAdapter = new AuctionAdapter();
        auctionAdapter.init(getContext(),listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(auctionAdapter);
    }

}