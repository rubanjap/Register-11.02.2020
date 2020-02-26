package com.register.me.view.fragments.Client.portfolio.addProducts;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.onurkaganaldemir.ktoastlib.KToast;
import com.register.me.R;
import com.register.me.model.data.model.QandA;
import com.register.me.presenter.AddProductPresenter;
import com.register.me.view.BaseActivity;
import com.register.me.view.BaseFragment;
import com.register.me.view.HomeActivity;
import com.register.me.view.fragmentmanager.manager.IFragment;
import com.thomashaertel.widget.MultiSpinner;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jennifer - AIT.
 */
public class AddProductFragment extends BaseFragment implements IFragment, AddProductPresenter.IAddProduct {

    private static final String FRAGMENT_NAME = "Add Product";

    @Inject
    AddProductPresenter addProductPresenter;
    private ArrayList<QandA> questList;

    @BindView(R.id.txtBtn)
    TextView AddEditBtn;
    @BindView(R.id.product_container)
    LinearLayout container;
    private String mTrue = "true";
    private String mFalse = "false";
    private String mSelect = "Select ";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injector().inject(this);
        addProductPresenter.init(getContext(), this);

        ((Activity) getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (addProductPresenter.getStatusAddOrEdit()) {
            questList = addProductPresenter.getQuestions();
            AddEditBtn.setText("Add Product");
        } else {
            addProductPresenter.setIsEdit();
            questList = addProductPresenter.getEditData();
            AddEditBtn.setText("Update Product");
        }
        buildUI();
    }

    private void buildUI() {
        View inflateView;
        LayoutInflater inflater = LayoutInflater.from(getContext());

        int i = 0;
        container.removeAllViews();
        for (QandA item : questList) {
            switch (item.getType()) {
                case 1:
                    inflateView = getEditTextView(inflater, i, item);
                    break;
                case 2:
                    inflateView = getRadioGroupView(inflater, i, item);

                    break;
                case 3:
                    inflateView = getSpinnerView(inflater, i, item);
                    break;
                case 4:
                    inflateView = getRadioRadioView(inflater, i, item);
                    break;
                case 5:
                    inflateView = getRadioSpinnerView(inflater, i, item);

                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + item.getType());
            }
            container.addView(inflateView);
            i++;
        }

    }

    @NotNull
    private View getRadioSpinnerView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_radio_spinner, null, false);
        ConstraintLayout subRS = inflateView.findViewById(R.id.sub_layout);
        TextView mainTextRS = inflateView.findViewById(R.id.itemTextTitle);
        TextView selectTxtRS = inflateView.findViewById(R.id.sub_spinner_text);
        RadioGroup rrGroupRS = inflateView.findViewById(R.id.sub_rdGroup);
        RadioButton rrYes = inflateView.findViewById(R.id.rdValue_yes);
        RadioButton rrNo = inflateView.findViewById(R.id.rdValue_no);
        MultiSpinner spinner = inflateView.findViewById(R.id.sub_spinnerValue);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        int finalI14 = i;
        String question = item.getQuestion();
        List<String> sublist = item.getSubQA().getSubList();

        if (item.getAnswer() != null && item.getAnswer().equals(mTrue)) {
            rrYes.setChecked(true);
            subRS.setVisibility(View.VISIBLE);
        } else if (item.getAnswer() != null && item.getAnswer().equals(mFalse)) {
            rrNo.setChecked(true);
            subRS.setVisibility(View.GONE);
        }
        mainTextRS.setText(question);

        String[] array;
        if (question.contains("sterilization")) {
            array = getContext().getResources().getStringArray(R.array.sterile);
            adapter.addAll(array);
            setSpinnerAdapter(spinner, finalI14, adapter, selectTxtRS, item.getSubQA().getQuestion());
            if (sublist != null) {
                updateSpinner(item, selectTxtRS, spinner, sublist, array);
            } else {
                selectTxtRS.setText(item.getSubQA().getQuestion());
            }
        } else {
            array = getContext().getResources().getStringArray(R.array.battery);
            adapter.addAll(array);
            setSpinnerAdapter(spinner, finalI14, adapter, selectTxtRS, item.getSubQA().getQuestion());
            if (sublist != null) {
                updateSpinner(item, selectTxtRS, spinner, sublist, array);
            }
        }
        rrGroupRS.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.rdValue_yes) {
                    subRS.setVisibility(View.VISIBLE);
                    selectTxtRS.setText(item.getSubQA().getQuestion());
                    questList.get(finalI14).setAnswer(mTrue);
                } else {
                    subRS.setVisibility(View.GONE);
                    questList.get(finalI14).setAnswer(mFalse);
                    questList.get(finalI14).getSubQA().setAnswer("");
                    boolean[] selected = new boolean[array.length];
                    for (int x = 0; x < array.length; x++) {
                        selected[x] = false;
                    }
                    spinner.setSelected(selected);

                }
            }
        });
        return inflateView;
    }

    @NotNull
    private View getRadioRadioView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_radio_radio, null, false);
        ConstraintLayout sub = inflateView.findViewById(R.id.sub);

        TextView rrTxtView = inflateView.findViewById(R.id.itemTextTitle);
        rrTxtView.setText(item.getQuestion());
        RadioGroup rrGroup = inflateView.findViewById(R.id.rdValue);
        RadioButton yes = inflateView.findViewById(R.id.rdValue_yes);
        RadioButton no = inflateView.findViewById(R.id.rdValue_no);
        if (item.getAnswer() != null && item.getAnswer().equals(mTrue)) {
            yes.setChecked(true);
            sub.setVisibility(View.VISIBLE);
        } else if (item.getAnswer() != null && item.getAnswer().equals(mFalse)) {
            no.setChecked(true);
            sub.setVisibility(View.GONE);
        }
        TextView rrSubTxtView = inflateView.findViewById(R.id.sub_itemTextTitle);
        rrSubTxtView.setText(item.getSubQA().getQuestion());
        RadioGroup rrSubGroup = inflateView.findViewById(R.id.sub_rdValue);
        RadioButton subYes = inflateView.findViewById(R.id.sub_rdValue_yes);
        RadioButton subNo = inflateView.findViewById(R.id.sub_rdValue_no);
        if (item.getAnswer() != null && item.getSubQA().getAnswer().equals(mTrue)) {
            subYes.setChecked(true);
        } else if (item.getAnswer() != null && item.getSubQA().getAnswer().equals(mFalse)) {
            subNo.setChecked(true);
        }
        int finalI13 = i;

        rrGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.rdValue_yes) {
                    sub.setVisibility(View.VISIBLE);
                    questList.get(finalI13).setAnswer(mTrue);
                } else {
                    sub.setVisibility(View.GONE);
                    questList.get(finalI13).setAnswer(mFalse);
                    questList.get(finalI13).getSubQA().setAnswer(mFalse);
                    rrSubGroup.clearCheck();
                }
            }
        });
        rrSubGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.sub_rdValue_yes) {
                    questList.get(finalI13).getSubQA().setAnswer(mTrue);
                } else {
                    questList.get(finalI13).getSubQA().setAnswer(mFalse);
                }
            }
        });
        return inflateView;
    }

    @NotNull
    private View getSpinnerView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_multispinner, null, false);
        TextView txtSpinnerTitle = inflateView.findViewById(R.id.textSpinnerTitle);
        TextView txtSelect = inflateView.findViewById(R.id.spinner_text);
        MultiSpinner mSpinner = (MultiSpinner) inflateView.findViewById(R.id.spinnerMulti);
        String question = item.getQuestion();
        txtSpinnerTitle.setText(question);
        mSpinner.setText(item.getAnswer());

        txtSelect.setText(mSelect + question);
        List<String> subList = item.getSubList();
        ArrayAdapter<String> adapter = null;
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        String[] array;
        boolean[] selected;
        int finalI;
        ArrayAdapter<String> finalAdapter;
        switch (question) {
            case "Product Classification":
                array = getContext().getResources().getStringArray(R.array.classification_type);
                adapter.addAll(array);
                finalI = i;
                finalAdapter = adapter;
                setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
                if (subList != null) {
                    updateSpinner(item, txtSelect, mSpinner, subList, array);
                }
                break;
            case "Connection Type":
                array = getContext().getResources().getStringArray(R.array.connection_type);
                adapter.addAll(array);
                finalI = i;
                finalAdapter = adapter;
                setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
                if (subList != null) {
                    updateSpinner(item, txtSelect, mSpinner, subList, array);
                }
                break;
            case "Indications for Use":
                array = getContext().getResources().getStringArray(R.array.indication);
                adapter.addAll(array);
                finalI = i;
                finalAdapter = adapter;
                setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
                if (subList != null) {
                    updateSpinner(item, txtSelect, mSpinner, subList, array);
                }
                break;
            case "Intended Population":
                array = getContext().getResources().getStringArray(R.array.intended_population);
                adapter.addAll(array);
                finalI = i;
                finalAdapter = adapter;
                setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
                if (subList != null) {
                    updateSpinner(item, txtSelect, mSpinner, subList, array);
                }
                break;
            case "Device Type":
                array = getContext().getResources().getStringArray(R.array.device_type);
                adapter.addAll(array);
                finalI = i;
                finalAdapter = adapter;
                setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
                if (subList != null) {
                    updateSpinner(item, txtSelect, mSpinner, subList, array);
                }
                break;
            case "Usage Environment":
                array = getContext().getResources().getStringArray(R.array.usage_env);
                adapter.addAll(array);
                finalI = i;
                finalAdapter = adapter;
                setSpinnerAdapter(mSpinner, finalI, finalAdapter, txtSelect, question);
                if (subList != null) {
                    updateSpinner(item, txtSelect, mSpinner, subList, array);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + question);
        }
        return inflateView;
    }

    private void updateSpinner(QandA item, TextView txtSelect, MultiSpinner mSpinner, List<String> subList, String[] array) {
        boolean[] selected;
        selected = addProductPresenter.getBoolArray(array, subList);
        mSpinner.setSelected(selected);
        mSpinner.setText("");
        if (item.getSubQA() != null) {
            String answer = item.getSubQA().getAnswer();
            txtSelect.setText(answer.replaceAll("\\[", "").replaceAll("\\]", ""));
        } else {
            String answer = item.getAnswer();
            txtSelect.setText(answer.replaceAll("\\[", "").replaceAll("\\]", ""));
        }
    }



    @NotNull
    private View getRadioGroupView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_radio_group, null, false);
        TextView txtRadioView = inflateView.findViewById(R.id.itemTextTitle);
        txtRadioView.setText(item.getQuestion());
        RadioGroup group = inflateView.findViewById(R.id.rdValue);
        RadioButton yes = inflateView.findViewById(R.id.rdValue_yes);
        RadioButton no = inflateView.findViewById(R.id.rdValue_no);

        if (item.getAnswer() != null && item.getAnswer().equals(mTrue)) {
            yes.setChecked(true);
        } else if (item.getAnswer() != null && item.getAnswer().equals(mFalse)) {
            no.setChecked(true);
        }
        int finalI12 = i;
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                if (id == R.id.rdValue_yes) {
                    questList.get(finalI12).setAnswer(mTrue);
                } else {
                    questList.get(finalI12).setAnswer(mFalse);
                }
            }
        });
        return inflateView;
    }

    @NotNull
    private View getEditTextView(LayoutInflater inflater, int i, QandA item) {
        View inflateView;
        inflateView = inflater.inflate(R.layout.item_edittext, null, false);
        TextView txtQuest = inflateView.findViewById(R.id.itemTxtTitle);
        EditText txtAns = inflateView.findViewById(R.id.itemEditValue);
        txtQuest.setText(item.getQuestion());
        txtAns.setText(item.getAnswer());
        int inputType = item.getInputType();
        txtAns.setInputType(addProductPresenter.getInputType(inputType));
        txtAns.setImeOptions(item.getAction() == 1 ? EditorInfo.IME_ACTION_NEXT : EditorInfo.IME_ACTION_DONE);

        int finalI1 = i;
        txtAns.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// Nil
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
// Nil

            }

            @Override
            public void afterTextChanged(Editable editable) {
                questList.get(finalI1).setAnswer(editable.toString());
            }
        });
        return inflateView;
    }

    private void setSpinnerAdapter(MultiSpinner mSpinner, int finalI, ArrayAdapter<String> finalAdapter, TextView txtSelect, String defaultText) {

        MultiSpinner.MultiSpinnerListener onSelectedListener = selected -> {
            mSpinner.setText("");
            ArrayList<String> list = new ArrayList<>();

            for (int i = 0; i < selected.length; i++) {
                if (selected[i]) {
                    String item = finalAdapter.getItem(i).trim();
                    list.add(item);
                }
            }

            if (list.size() != 0) {
                String answer = addProductPresenter.getStringList(list);
                if (questList.get(finalI).getSubQA() != null) {
                    questList.get(finalI).getSubQA().setAnswer(answer);
                } else {
                    questList.get(finalI).setAnswer(answer);
                }
                txtSelect.setText(answer);
            } else {
                txtSelect.setText(mSelect + defaultText);
            }
        };
        mSpinner.setAdapter(finalAdapter, false, onSelectedListener);
    }



    @OnClick(R.id.card_add)
    public void onClickAdd() {
        addProductPresenter.validateAnswers(questList);
    }


    @OnClick(R.id.card_cancel)
    public void onClickCancel() {
        fragmentChannel.popUp();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((HomeActivity) getActivity()).setHeaderText(getResources().getString(R.string.add_product));
    }


    @Override
    public String getFragmentName() {
        return FRAGMENT_NAME;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_product;
    }

    public static AddProductFragment newInstance() {
        return new AddProductFragment();
    }

    @Override
    public void showErrorMessage(String message) {
        if (message.toLowerCase().contains("success")) {
            ((BaseActivity)getContext()).onBackPressed();
        }
        KToast.customColorToast((Activity) getContext(), message, Gravity.BOTTOM, KToast.LENGTH_AUTO, R.color.red);
    }
}
