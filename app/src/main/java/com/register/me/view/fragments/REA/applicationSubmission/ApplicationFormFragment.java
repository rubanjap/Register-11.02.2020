package com.register.me.view.fragments.REA.applicationSubmission;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.register.me.R;
import com.register.me.model.data.model.QandA;
import com.register.me.view.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 03:05.
 */
public class ApplicationFormFragment extends BaseFragment {


    private ArrayList<QandA> questionList;
    @BindView(R.id.container)
    LinearLayout container;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_application_form;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        questionList = new ArrayList<>();
        questionList.add(new QandA("How many product registrations have you completed", "", 1));
        questionList.add(new QandA("Product Description", "", 1));
        questionList.add(new QandA("Product Classification", "", 1));
        questionList.add(new QandA("Have any been IVD?", "", 2));
        questionList.add(new QandA("Have any been life supporting / life sustaining?", "", 2));
        questionList.add(new QandA("Have any had direct or indirect patients contacting components?", "", 2));
        questionList.add(new QandA("How many registered products used software and / or firmware?", "", 3));
        questionList.add(new QandA("Select Connection Type", "", 1));
        questionList.add(new QandA("How many registered products were sterilized products?", "", 3));
        questionList.add(new QandA("Select Product Type", "", 3));
        questionList.add(new QandA("Select Usage Environment", "", 3));
        questionList.add(new QandA("Have any been combination devices?", "", 2));
        questionList.add(new QandA("Have any been electrical?", "", 2));
        questionList.add(new QandA("Have any been adult (pediatric)?", "", 2));

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View inflateView;
        container.removeAllViews();
        for (QandA item : questionList) {
            switch (item.getType()) {
                case 1:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext, null, false);
                    TextView txtView = inflateView.findViewById(R.id.itemTxtTitle);
                    txtView.setText(item.getQuestion());
                    break;
                case 2:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_radio_group, null, false);
                    TextView txtRadioView = inflateView.findViewById(R.id.itemTextTitle);
                    txtRadioView.setText(item.getQuestion());

                    break;
                case 3:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, null, false);
                    TextView txtSpinnerView = inflateView.findViewById(R.id.textSpinnerTitle);
                    txtSpinnerView.setText(item.getQuestion());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getType());
            }
            container.addView(inflateView);

        }

    }
}
