package com.register.me.presenter;

import android.content.Context;

import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.GetProductModel;
import com.register.me.model.data.model.KeyValue;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;

import javax.inject.Inject;


public class ViewProductPresenter {
    Context context;
    @Inject
    Constants constants;

    public void init(Context context) {
        this.context = context;
        ((BaseActivity) context).injector().inject(this);
    }


    public ArrayList<KeyValue> extractData() {
        if (constants.getSelectedList()!=null) {
            GetProductModel.Product_ data = constants.getSelectedList().getProduct();
            ArrayList<KeyValue> kv = new ArrayList<>();
            kv.add(new KeyValue(context.getResources().getString(R.string.product_number),data.getProductNumber(),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.product_name),data.getProductName(),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.product_classification),null,data.getDeviceClassification()));
            kv.add(new KeyValue(context.getResources().getString(R.string.is_it_idv),String.valueOf(data.getIvd()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.is_it_life_supporting_life_sustaining),String.valueOf(data.getLifeSupporting()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.any_direct_or_indirect_patients_contacting_components),String.valueOf(data.getPatientContactComponent()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.if_yes_is_it_implant),String.valueOf(data.getIsitAnImplant()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.does_the_product_use_software_firmware),String.valueOf(data.getUseSoftware()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.if_yes_does_it_contains_digital_health_technology),String.valueOf(data.getDigitalHealthTechnology()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.connection_type),null,data.getConnectionType()));
            kv.add(new KeyValue(context.getResources().getString(R.string.does_the_product_need_sterlization),String.valueOf(data.getRequiredSterilization()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.sterilization_type),null,data.getSterilizationType()));
            kv.add(new KeyValue(context.getResources().getString(R.string.indications_for_use),null,data.getIndications()));
            kv.add(new KeyValue(context.getResources().getString(R.string.intended_population),null,data.getIntendedPopulation()));
            kv.add(new KeyValue(context.getResources().getString(R.string.device_type),null,data.getDeviceType()));
            kv.add(new KeyValue(context.getResources().getString(R.string.usage_environment),null,data.getUseEnvironment()));
            kv.add(new KeyValue(context.getResources().getString(R.string.is_this_a_combination_product),String.valueOf(data.getIsCombinationDevice()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.is_the_product_electrical),String.valueOf(data.getIsElectricalDevice()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.electrical_product_type),null,data.getElectricalDeviceType()));
            kv.add(new KeyValue(context.getResources().getString(R.string.is_the_device_for_pediatric_use),String.valueOf(data.getIsPediatric()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.does_the_device_emit_radiation),String.valueOf(data.getEmitRadiation()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.do_you_have_substance_of_biological_origin_in_your_device),String.valueOf(data.getBiologicalOrigin()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.will_the_device_be_reprocessed_for_resale),String.valueOf(data.getReprocessSUD()),null));
            kv.add(new KeyValue(context.getResources().getString(R.string.product_description),data.getDescription(),null));
            return kv;
        }
        return null;
    }
}
