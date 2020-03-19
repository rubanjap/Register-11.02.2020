package com.register.me.view.fragments.REA.applicationSubmission;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

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
import java.util.Objects;

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
    LinearLayout base;
    @BindView(R.id.btn_layout)
    ConstraintLayout layoutBTN;
    @BindView(R.id.sub_header)
    ConstraintLayout subHeader;
    @BindView(R.id.disableClick)
    View disableView;
    @BindView(R.id.img_Edit)
    ImageView editImg;
    private ArrayList<QandA> info;
    @BindView(R.id.progressbar)
    ConstraintLayout progressLayout;
    @BindView(R.id.layPbar)
    ConstraintLayout layoutPbar;
    @BindView(R.id.scroll)
    ScrollView scrollView;

    @Inject
    PersonalInfoPresenter presenter;
    private boolean isExit;

    public static IFragment newInstance() {
        return new PersonalInfoFragment();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal_info;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        presenter.init(getContext(), this);
        fragmentChannel.setTitle(getResources().getString(R.string.profile_details));

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(presenter.getRole() == 1){
            subHeader.setVisibility(View.GONE);
            disableView.setVisibility(View.GONE);
            layoutBTN.setVisibility(View.VISIBLE);
        }else {
            layoutBTN.setVisibility(View.GONE);
        }
        presenter.getUserDetails();
    }

    private void updateUI() {

        View inflateView;
        container.removeAllViews();
        int i = 0;
        for (QandA item : info) {
            String question = item.getQuestion();
            String answer = item.getAnswer();
            switch (item.getType()) {
                /*
                 * case 1 : Edit Text
                 * case 2 : Radio Button
                 * case 3 : Spinner */

                case 1:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_edittext, null, false);
                    TextView txtQuest = inflateView.findViewById(R.id.itemTxtTitle);
                    EditText txtAns = inflateView.findViewById(R.id.itemEditValue);
                    txtQuest.setText(question);
                    txtAns.setText(answer);
                    txtAns.setImeOptions(item.getAction() == 1 ? EditorInfo.IME_ACTION_NEXT : EditorInfo.IME_ACTION_DONE);

                    int inputType = item.getInputType();
                   txtAns.setInputType(presenter.getInputType(inputType));


                    int finalI = i;
                    if (question.equals("Email")) {
                        txtAns.setFocusable(false);
                    } else {
                        txtAns.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                            Log.d("onTextChanged", s.toString());
                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        info.get(finalI).setAnswer(s.toString());
//                                        Log.d("afterTextChanged", s.toString());
                                    }
                                }).start();

                            }
                        });
                    }
                    break;
                case 2:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_radio_group, null, false);
                    TextView txtRadioView = inflateView.findViewById(R.id.itemTextTitle);
                    txtRadioView.setText(question);
                    RadioGroup group = inflateView.findViewById(R.id.rdValue);
                    RadioButton email = inflateView.findViewById(R.id.rdValue_yes);
                    RadioButton sms = inflateView.findViewById(R.id.rdValue_no);
                    email.setText("Email");
                    if(presenter.getRole()==0){
                    sms.setVisibility(View.GONE);}
                    else {
                        sms.setVisibility(View.VISIBLE);}
                    int finalI1 = i;
                    if (answer.equals("true")) {
                        email.setChecked(true);
                    } else {
                        email.setChecked(false);
                    }
                    group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (checkedId == R.id.rdValue_yes) {
                                info.get(finalI1).setAnswer("true");
                            }
                        }
                    });

                    break;
                case 3:
                    inflateView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, null, false);
                    TextView txtSpinnerView = inflateView.findViewById(R.id.textSpinnerTitle);
                    txtSpinnerView.setText(question);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getType());
            }
            container.addView(inflateView);
            base.setVisibility(View.VISIBLE);

            i = i + 1;
        }
    }

    @Override
    public String getFragmentName() {
        return "PersonalInfo";
    }

    @Override
    public void showErrorMessage(String message) {
        KToast.customColorToast((Activity) Objects.requireNonNull(getContext()), message, Gravity.BOTTOM, KToast.LENGTH_AUTO, R.color.red);

    }

    @Override
    public void updateUI(ArrayList<QandA> body) {
        if(isExit) {
            fragmentChannel.updateNavigation();
            return;
        }
        info = new ArrayList<QandA>();
        info = (ArrayList<QandA>) body.clone();
        updateUI();

    }

    @Override
    public void errorFetchingData(String s) {
        showErrorMessage(s);
    }

    @Override
    public void exitScreen() {
        isExit =true;
        presenter.getUserDetails();
        fragmentChannel.popUp();
    }

    @Override
    public void dismissProgress() {
        progressLayout.setVisibility(View.GONE);
        layoutPbar.setVisibility(View.GONE);
    }

    @Override
    public void showProgress() {
        progressLayout.setVisibility(View.VISIBLE);
        layoutPbar.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.disableClick)
    public void disableClick() {
        KToast.normalToast(getActivity(), getContext().getResources().getString(R.string.edit_icon_alert), Gravity.BOTTOM, KToast.LENGTH_AUTO);

    }

    @OnClick(R.id.img_Edit)
    public void onClickEdit() {
        disableView.setVisibility(View.GONE);
        editImg.setVisibility(View.GONE);
        layoutBTN.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.card_cancel)
    public void onClickCancel() {
        fragmentChannel.popUp();
    }


    @OnClick(R.id.card_update)
    public void onClickUpdate() {
        presenter.validate(info);
//        presenter.updateUser(info);

    }
}
