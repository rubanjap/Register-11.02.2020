package com.register.me.view.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.register.me.model.data.model.CrreList;

import java.util.List;

/**
 * Created by Jennifer - AIT on 10-03-2020PM 12:54.
 */
public class SpinAdapter<T> extends ArrayAdapter<T> {

    private final Object obj;
    private Context context;
    private List<T> values;

    public SpinAdapter(Context context, int textViewResourceId, List<T> values, Object object) {
        super(context, textViewResourceId, values);
        this.context = context;
        this.values = values;
        this.obj =object;
    }

    public int getCount() {
        return values.size();
    }

    public T getItem(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        String text = "";
        if (obj instanceof CrreList) {
            CrreList.Expertlist objects = (CrreList.Expertlist) values.get(position);
            text = objects.getExpertName();
        }
        label.setText(text);
        return label;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        String text = "";
        if (obj instanceof CrreList) {
            CrreList.Expertlist objects = (CrreList.Expertlist) values.get(position);
            text = "   "+objects.getExpertName();
        }
        label.setText(text);

        return label;
    }
}
