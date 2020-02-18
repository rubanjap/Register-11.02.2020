package com.register.me.view.fragments.REA.applicationSubmission;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.model.QandA;
import com.register.me.presenter.PersonalInfoPresenter;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT on 13-02-2020PM 03:04.
 */
public class PersonalInfoFragment extends BaseFragment implements IFragment, PersonalInfoPresenter.IPersonalInfo {

    @BindView(R.id.profile_container)
    LinearLayout container;
    @BindView(R.id.base)
    ScrollView base;
    @BindView(R.id.btn_layout)
    ConstraintLayout layoutBTN;
    @BindView(R.id.disableClick)
    View disableView;
    @BindView(R.id.img_save)
    View saveImg;
    @BindView(R.id.img_Edit)
    ImageView editSaveImg;
    private ArrayList<QandA> info;

    @Inject
    PersonalInfoPresenter presenter;

    public static IFragment newInstance() {
        return new PersonalInfoFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_info;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.profile_details));

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
//        getQandA();
        presenter.init(getContext(), this);
        presenter.getUserDetails();

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        updateUI();
    }

    private void updateUI() {

        View inflateView;
        container.removeAllViews();
        for (QandA item : info) {
            switch (item.getType()) {
                case 1:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext, null, false);
                    TextView txtView = inflateView.findViewById(R.id.itemTxtTitle);
                    TextView txtValue = inflateView.findViewById(R.id.itemEditValue);
                    txtView.setText(item.getQuestion());
                    txtValue.setText(item.getAnswer());
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
            base.setVisibility(View.VISIBLE);
            layoutBTN.setVisibility(View.GONE);

        }
    }

    private void getQandA() {
        info = new ArrayList<QandA>();
        info.add(new QandA("First Name", "", 1));
        info.add(new QandA("Last Name", "", 1));
        info.add(new QandA("Email", "", 1));
        info.add(new QandA("Telephone", "", 1));
        info.add(new QandA("Cell Phone", "", 1));
       /* info.add(new QandA("Company Name","",1));
        info.add(new QandA("Address 1","",1));
        info.add(new QandA("Address 2","",1));
        info.add(new QandA("City","",1));
        info.add(new QandA("State","",1));
        info.add(new QandA("Postal Code","",1));
        info.add(new QandA("Country","",1));*/
        info.add(new QandA("Notification", "", 2));
    }

    @Override
    public String getFragmentName() {
        return "PersonalInfo";
    }

    @Override
    public void showErrorMessage(String message) {
        KToast.customColorToast((Activity) getContext(), message, Gravity.BOTTOM, KToast.LENGTH_SHORT, R.color.red);

    }

    @Override
    public void updateUI(ArrayList<QandA> body) {
        info = new ArrayList<QandA>();
        info = (ArrayList<QandA>) body.clone();
        updateUI();

    }

    @Override
    public void errorFetchingData(String s) {
        showErrorMessage(s);
    }

    @OnClick(R.id.disableClick)
    public void disableClick() {

    }

    @OnClick(R.id.img_Edit)
    public void onClickEdit(){
        Drawable.ConstantState constantState = editSaveImg.getDrawable().getConstantState();
        Drawable.ConstantState constantState1 = getContext().getResources().getDrawable(R.drawable.edit_icon,null).getConstantState();
        Drawable.ConstantState constantState2 = getContext().getResources().getDrawable(R.drawable.ic_highlight_off_black_24dp,null).getConstantState();
        if(constantState == constantState1){
        disableView.setVisibility(View.GONE);
        editSaveImg.setImageResource(R.drawable.ic_highlight_off_black_24dp);
        saveImg.setVisibility(View.VISIBLE);}
        else if(constantState == constantState2) {
                disableView.setVisibility(View.VISIBLE);
                editSaveImg.setImageResource(R.drawable.edit_icon);
                saveImg.setVisibility(View.GONE);
            }
    }

    @OnClick(R.id.img_save)
    public void updateProfile(){
        Toast.makeText(getContext(), "Update Profile", Toast.LENGTH_SHORT).show();
    }
}
