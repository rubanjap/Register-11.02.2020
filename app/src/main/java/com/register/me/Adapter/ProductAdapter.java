package com.register.me.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    int clickedPosition = -1;
    Context context;
    private OnItemClickListener listener;

    public void init(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        holder.productNumber.setText(String.valueOf(position + 1));
       /* if (clickedPosition == position) {
            if (holder.countrylist.getVisibility() != View.VISIBLE) {
                holder.countrylist.setVisibility(View.VISIBLE);
                holder.add_minus_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_minus_24));
            } else {
                holder.countrylist.setVisibility(View.GONE);
                holder.add_minus_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add24dp));
            }
        } else {
            holder.countrylist.setVisibility(View.GONE);
            holder.add_minus_icon.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add24dp));
        }*/
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        LinearLayout base;
        TextView productNumber;
        ImageView view_icon;
        ImageView auction_icon;
        ImageView send_icon;
        ImageView edit_icon;
        ImageView expand_icon;
        ImageView error_icon;
        ImageView country_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            base = itemView.findViewById(R.id.productHeader);
//            countrylist = itemView.findViewById(R.id.country_layout);
            productNumber = itemView.findViewById(R.id.pNumber);

            view_icon = itemView.findViewById(R.id.view_icon);
            auction_icon = itemView.findViewById(R.id.auction_icon);
            send_icon = itemView.findViewById(R.id.send_icon);
            edit_icon = itemView.findViewById(R.id.edit_icon);
            expand_icon = itemView.findViewById(R.id.expand_icon);
            error_icon = itemView.findViewById(R.id.error_icon);
            country_icon = itemView.findViewById(R.id.country_icon);

            view_icon.setOnClickListener(this);
            auction_icon.setOnClickListener(this);
            send_icon.setOnClickListener(this);
            edit_icon.setOnClickListener(this);
            expand_icon.setOnClickListener(this);
            error_icon.setOnClickListener(this);
            country_icon.setOnClickListener(this);


        /*    base.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickedPosition = getAdapterPosition();
                    notifyDataSetChanged();

                }
            });*/

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.view_icon:
                    listener.onViewIconClick(getAdapterPosition());
                    break;
                case R.id.auction_icon:
                    listener.onAuctionIconClick(getAdapterPosition());
                    break;
                case R.id.send_icon:
                    listener.onSendIconClick(getAdapterPosition());
                    break;
                case R.id.edit_icon:
                    Toast.makeText(context, "Edit Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.expand_icon:
                    Toast.makeText(context, "Expand Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.error_icon:
                    Toast.makeText(context, "Error Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.country_icon:
                    Toast.makeText(context, "Country Clicked", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public interface OnItemClickListener {

        void onViewIconClick(int position);

        void onAuctionIconClick(int position);

        void onSendIconClick(int adapterPosition);
    }
}
