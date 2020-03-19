package com.register.me.view.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.ActiveCompProject;

import java.util.ArrayList;

public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.ViewHolder> {

    Context context;
    private int isActive =1;
    private int isComplete =2;
    private ArrayList<Object> dataList;
    private ItemClickListener listener;

    public void init(Context context, ArrayList<Object> list,ItemClickListener listener) {
        this.context = context;
        this.dataList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.project_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int itemViewType = holder.getItemViewType();
   switch (itemViewType){
       case 1:
           holder.bindActive(dataList.get(position));
           break;
       case 2:
           holder.bindCompleted(dataList.get(position));
           break;
       default:
           throw new IllegalStateException("Unexpected value: " + itemViewType);
   }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView pay;
        ImageView viewIcon;
        TextView bidCountry;
        TextView pNumber;
        TextView pName;
        TextView txtBidCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pNumber = itemView.findViewById(R.id.pNumber);
            pName = itemView.findViewById(R.id.pName);
            txtBidCountry = itemView.findViewById(R.id.txtBid_country);
            pay = itemView.findViewById(R.id.payIcon);
            bidCountry = itemView.findViewById(R.id.txtBid_country);
            viewIcon = itemView.findViewById(R.id.view_icon);
        }

        public void bindActive(Object object) {
            ActiveCompProject.ActiveProjectDetail actProject = (ActiveCompProject.ActiveProjectDetail) object;
            pNumber.setText(actProject.getProductnumber());
            pName.setText(actProject.getRegistrationexpert());
            txtBidCountry.setText(actProject.getBidamount());
            viewIcon.setOnClickListener(view ->listener.onItemClick(actProject.getProjectId()) );
        }

        public void bindCompleted(Object object) {
            ActiveCompProject.CompletedProjectDetail compProject = (ActiveCompProject.CompletedProjectDetail) object;
            pNumber.setText(compProject.getProductnumber());
            pName.setText(compProject.getRegistrationexpert());
            txtBidCountry.setText(compProject.getCountry());
            pay.setVisibility(View.GONE);
            viewIcon.setOnClickListener(view ->listener.onItemClick(compProject.getProjectId()) );
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position) instanceof ActiveCompProject.ActiveProjectDetail){
            return isActive;
        }else if(dataList.get(position) instanceof ActiveCompProject.CompletedProjectDetail){
            return isComplete;
        }
        return -1;
    }

    public interface ItemClickListener{
        void onItemClick(int position);
    }
}
