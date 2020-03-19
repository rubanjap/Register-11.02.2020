package com.register.me.view.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.register.me.R;
import com.register.me.model.data.model.QandA;

import java.util.List;

/**
 * Created by Jennifer - AIT on 20-02-2020PM 06:20.
 */
public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<QandA> dataList;
    private final int EDIT = 1;
    private final int RADIO = 2;
    private final int SPINNER = 3;

    public ItemAdapter(List<QandA> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder =null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType){
            case EDIT:
                View editView = inflater.inflate(R.layout.item_edittext,parent,false);
                viewHolder = new EditViewHolder(editView);
                break;
            case RADIO:
                View radioView = inflater.inflate(R.layout.item_radio_group,parent,false);
                viewHolder = new RadioViewHolder(radioView);
                break;
            case SPINNER:
                View spinnerView = inflater.inflate(R.layout.item_spinner,parent,false);
                viewHolder = new SpinnerViewHolder(spinnerView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case EDIT:
                EditViewHolder editViewHolder = (EditViewHolder)holder;
                configureEditViewHolder(editViewHolder,position);
                break;
            case RADIO:
                RadioViewHolder radioViewHolder = (RadioViewHolder)holder;
                configureRadioViewHolder(radioViewHolder,position);
                break;
            case SPINNER:
                SpinnerViewHolder spinnerViewHolder = (SpinnerViewHolder)holder;
                configureSpinnerViewHolder(spinnerViewHolder,position);
                break;
        }
    }

    private void configureSpinnerViewHolder(SpinnerViewHolder spinnerViewHolder, int position) {

    }

    private void configureRadioViewHolder(RadioViewHolder radioViewHolder, int position) {
    }

    private void configureEditViewHolder(EditViewHolder editViewHolder, int position) {
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (dataList.get(position).getType()) {
            case 1:
                return EDIT;
            case 2:
                return RADIO;
            case 3:
                return SPINNER;
            default:
                return -1;
        }
    }

    private class EditViewHolder extends RecyclerView.ViewHolder {
        public EditViewHolder(View editView) {
            super(editView);
        }
    }

    private class RadioViewHolder extends RecyclerView.ViewHolder {
        public RadioViewHolder(View radioView) {
            super(radioView);
        }
    }

    private class SpinnerViewHolder extends RecyclerView.ViewHolder {
        public SpinnerViewHolder(View spinnerView) {
            super(spinnerView);
        }
    }
}
