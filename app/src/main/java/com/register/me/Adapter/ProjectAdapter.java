package com.register.me.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.view.fragments.dashboardClient.activeProjects.CompletedProjectFragment;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    Context context;
    private int from;

    public void init(Context context, int from) {
        this.context = context;
        this.from = from;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (from == 0) {
        holder.bid_country.setText("$ 1000.0");
        } else {
            holder.bid_country.setText("India");
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pay;
        TextView bid_country;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pay = itemView.findViewById(R.id.payIcon);
            bid_country = itemView.findViewById(R.id.txtBid_country);
            if (from == 0) {
                pay.setVisibility(View.VISIBLE);
            } else {
                pay.setVisibility(View.GONE);
            }
        }
    }

}
