package com.register.me.presenter;

import android.content.Context;

import com.google.gson.JsonObject;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.NetworkCall;
import com.register.me.R;
import com.register.me.model.JsonBuilder;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.AddProductModel;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.QandA;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class AddProductPresenter implements NetworkCall.NetworkAddProductInterface {
    private Context context;
    private IAddProduct listener;
    @Inject
    Utils utils;
    @Inject
    Constants constants;
    @Inject
    CacheRepo repo;
    @Inject
    Retrofit retrofit;
    @Inject
    NetworkCall networkCall;
    @Inject
    JsonBuilder builder;
    private ArrayList<QandA> questList;

    boolean isEdit = false;
    private Integer productId;

    public void init(Context context, IAddProduct listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
    }

    public ArrayList<QandA> getQuestions() {
        ArrayList<QandA> quest = new ArrayList<>();

        /* TYPE
         * case 1 : Edit Text
         * case 2 : Radio Button
         * case 3 : Spinner */

        /* INPUT TYPE
         * 1 - text
         * 2 - email
         * 3- password
         * */
        /* ACTION
         * 1 - action_next
         * 2 - action_done
         *
         * */
        /*context.getString(R.string.is_this_a_combination_product)*/
        quest.add(new QandA(context.getString(R.string.product_number), null, 1, 1, 1, "productnumber", null, null));
        quest.add(new QandA(context.getString(R.string.product_name), null, 1, 1, 1, "productname", null, null));
        quest.add(new QandA(context.getString(R.string.product_description), null, 1, 1, 2, "productdesc", null, null));
        quest.add(new QandA(context.getString(R.string.product_classification), null, 3, 0, 0, "deviceclassification", null, null));
        quest.add(new QandA(context.getString(R.string.is_it_idv), null, 2, 0, 0, "isivd", null, null));
        quest.add(new QandA(context.getString(R.string.is_this_a_combination_product), null, 2, 0, 0, "iscombination", null, null));
        quest.add(new QandA(context.getResources().getString(R.string.Is_the_device_life_supporting), null, 2, 0, 0, "lifesupportorsustain", null, null));
        QandA implant = new QandA(context.getResources().getString(R.string.Is_the_device_or_component_an_implant), null, 2, 0, 0, "isimplant", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.are_there_any_direct_or_indirect_patient_contacting_components), null, 4, 0, 0, "patientcomponent", implant, null));
        /*******isImplant****************/
        QandA isDigital = new QandA(context.getResources().getString(R.string.is_the_device_digital_health_technology_or_does_it_contain_digital_health_technology), null, 2, 0, 0, "isdigitalhealth", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_use_software_firmware), null, 4, 0, 0, "softorfirmware", isDigital, null));
        /*******isdigitalhealth****************/
        quest.add(new QandA(context.getString(R.string.connection_type), null, 3, 0, 0, "conntype", null, null));
        QandA sterile = new QandA(context.getResources().getString(R.string.select_the_type_of_sterile_processing), null, 3, 0, 0, "sterileprocess", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_or_a_component_need_sterilization_by_the_manufacturer_or_user), null, 5, 0, 0, "sterilization", sterile, null));
        /*******sterileprocess****************/
        quest.add(new QandA(context.getString(R.string.indications_for_use), null, 3, 0, 0, "indications", null, null));
        quest.add(new QandA(context.getString(R.string.intended_population), null, 3, 0, 0, "indentpopulation", null, null));
        quest.add(new QandA(context.getString(R.string.device_type), null, 3, 0, 0, "devicetype", null, null));
        quest.add(new QandA(context.getString(R.string.usage_environment), null, 3, 0, 0, "useenvironment", null, null));
        QandA deviceType = new QandA(context.getResources().getString(R.string.select_electrical_device_type), null, 3, 0, 0, "eledevicetype", null, null);
        quest.add(new QandA(context.getString(R.string.is_the_product_electrical), null, 5, 0, 0, "iselectrical", deviceType, null));
        /**********eledevicetype***************/
        quest.add(new QandA(context.getString(R.string.is_the_device_for_pediatric_use), null, 2, 0, 0, "ispediatric", null, null));
        quest.add(new QandA(context.getString(R.string.does_the_device_emit_radiation), null, 2, 0, 0, "isemitrad", null, null));
        quest.add(new QandA(context.getString(R.string.do_you_have_substance_of_biological_origin_in_your_device), null, 2, 0, 0, "biolorigin", null, null));
        quest.add(new QandA(context.getString(R.string.will_the_device_be_reprocessed_for_resale), null, 2, 0, 0, "isresale", null, null));

        return quest;
    }

    public boolean getStatusAddOrEdit() {
        return constants.getSelectedList() == null;
    }

    public void setIsEdit() {
        isEdit = true;
    }

    public ArrayList<QandA> getEditData() {
        ArrayList<QandA> quest = new ArrayList<>();
        GetProductModel.Product_ data = constants.getSelectedList().getProduct();
        productId = constants.getSelectedList().getProductId();

        quest.add(new QandA(context.getString(R.string.product_number), data.getProductNumber(), 1, 1, 1, "productnumber", null, null));
        quest.add(new QandA(context.getString(R.string.product_name), data.getProductName(), 1, 1, 1, "productname", null, null));
        quest.add(new QandA(context.getString(R.string.product_description), data.getDescription(), 1, 1, 2, "productdesc", null, null));
        quest.add(new QandA(context.getString(R.string.product_classification), getStringList((ArrayList<String>) data.getDeviceClassification()), 3, 0, 0, "deviceclassification", null, data.getDeviceClassification()));
        quest.add(new QandA(context.getString(R.string.is_it_idv), String.valueOf(data.getIvd()), 2, 0, 0, "isivd", null, null));
        quest.add(new QandA(context.getString(R.string.is_this_a_combination_product), String.valueOf(data.getIsCombinationDevice()), 2, 0, 0, "iscombination", null, null));
        quest.add(new QandA(context.getResources().getString(R.string.Is_the_device_life_supporting), String.valueOf(data.getLifeSupporting()), 2, 0, 0, "lifesupportorsustain", null, null));
        QandA implant = new QandA(context.getResources().getString(R.string.Is_the_device_or_component_an_implant), String.valueOf(data.getIsitAnImplant()), 2, 0, 0, "isimplant", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.are_there_any_direct_or_indirect_patient_contacting_components), String.valueOf(data.getPatientContactComponent()), 4, 0, 0, "patientcomponent", implant, null));
        QandA isDigital = new QandA(context.getResources().getString(R.string.is_the_device_digital_health_technology_or_does_it_contain_digital_health_technology), String.valueOf(data.getDigitalHealthTechnology()), 2, 0, 0, "isdigitalhealth", null, null);
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_use_software_firmware), String.valueOf(data.getUseSoftware()), 4, 0, 0, "softorfirmware", isDigital, null));
        quest.add(new QandA(context.getString(R.string.connection_type), getStringList((ArrayList<String>) data.getConnectionType()), 3, 0, 0, "conntype", null, data.getConnectionType()));

        QandA sterile = new QandA(context.getResources().getString(R.string.select_the_type_of_sterile_processing), getStringList((ArrayList<String>) data.getSterilizationType()), 3, 0, 0, "sterileprocess", null, data.getSterilizationType());
        quest.add(new QandA(context.getResources().getString(R.string.does_the_device_or_a_component_need_sterilization_by_the_manufacturer_or_user), String.valueOf(data.getRequiredSterilization()), 5, 0, 0, "sterilization", sterile, null));

        quest.add(new QandA(context.getString(R.string.indications_for_use), getStringList((ArrayList<String>) data.getIndications()), 3, 0, 0, "indications", null, data.getIndications()));
        quest.add(new QandA(context.getString(R.string.intended_population), getStringList((ArrayList<String>)data.getIntendedPopulation()), 3, 0, 0, "indentpopulation", null, data.getIntendedPopulation()));
        quest.add(new QandA(context.getString(R.string.device_type), getStringList((ArrayList<String>) data.getDeviceType()), 3, 0, 0, "devicetype", null, data.getDeviceType()));
        quest.add(new QandA(context.getString(R.string.usage_environment), getStringList((ArrayList<String>) data.getUseEnvironment()), 3, 0, 0, "useenvironment", null, data.getUseEnvironment()));

        QandA deviceType = new QandA(context.getResources().getString(R.string.select_electrical_device_type), getStringList((ArrayList<String>) data.getElectricalDeviceType()), 3, 0, 0, "eledevicetype", null, data.getElectricalDeviceType());
        quest.add(new QandA(context.getString(R.string.is_the_product_electrical), String.valueOf(data.getIsElectricalDevice()), 5, 0, 0, "iselectrical", deviceType, null));

        quest.add(new QandA(context.getString(R.string.is_the_device_for_pediatric_use), String.valueOf(data.getIsPediatric()), 2, 0, 0, "ispediatric", null, null));
        quest.add(new QandA(context.getString(R.string.does_the_device_emit_radiation), String.valueOf(data.getEmitRadiation()), 2, 0, 0, "isemitrad", null, null));
        quest.add(new QandA(context.getString(R.string.do_you_have_substance_of_biological_origin_in_your_device), String.valueOf(data.getBiologicalOrigin()), 2, 0, 0, "biolorigin", null, null));
        quest.add(new QandA(context.getString(R.string.will_the_device_be_reprocessed_for_resale), String.valueOf(data.getReprocessSUD()), 2, 0, 0, "isresale", null, null));
        return quest;
    }

    private String replaceText(String data){
        return  data.replaceAll("\\[", "").replaceAll("\\]", "");
    }

    public String getStringList(ArrayList<String> list) {
        StringBuilder builder = new StringBuilder();
        int x = 0;
        for (String data : list) {
            if (x != list.size() - 1) {
                builder.append(data.trim()).append(",");
            } else {
                builder.append(data.trim());
            }
            x++;
        }
        return builder.toString();
    }

    public boolean[] getBoolArray(String[] array, List<String> subList) {
        boolean[] selected = new boolean[array.length];
        int i = 0;
        boolean isEqual = false;
        for (String string : array) {
            for (String sub : subList) {
                if (string.equals(sub.trim())) {
                    isEqual = true;
                    break;
                }
            }
            selected[i] = isEqual;
            isEqual = false;
            i++;
        }
        return selected;
    }


    public void validateAnswers(ArrayList<QandA> questList) {
        for (QandA item : questList) {
            String ans = item.getAnswer();
            if (ans == null) {
                listener.showErrorMessage("All fields are mandatory");
                return;
            }
        }
        this.questList = questList;
        apicall();
    }

    private void apicall() {
        if (utils.isOnline(context)) {
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getCACHE_TOKEN());
            JsonObject data = builder.addProductJson(questList);
            if (isEdit) {
                networkCall.editProduct(apiInterface, token, data,productId, this);
            } else {
                networkCall.addProduct(apiInterface, token, data, this);
            }
        } else {
            listener.showErrorMessage(context.getResources().getString(R.string.network_alert));
        }
    }

    public int getInputType(int inputType) {
        return utils.getInputType(inputType);
    }

    @Override
    public void onProductAddedSuccess(AddProductModel body) {
        if (body.getData().getStatus()) {
            listener.showErrorMessage("Product Added Successfully");
        }
    }

    @Override
    public void onProductAddedFailed(String s) {
        listener.showErrorMessage(s);
    }

    @Override
    public void onProductUpdatedSuccess(AddProductModel body) {
        if (body.getData().getStatus()) {
            listener.showErrorMessage("Product Updated Successfully");
        }
    }

    @Override
    public void onProductUpdatedFailed(String s) {
        listener.showErrorMessage(s);
    }


    public interface IAddProduct {
        void showErrorMessage(String message);
    }
}
