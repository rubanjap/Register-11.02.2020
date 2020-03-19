package com.register.me.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.register.me.APIs.ApiInterface;
import com.register.me.APIs.ClientNetworkCall;
import com.register.me.R;
import com.register.me.model.data.Constants;
import com.register.me.model.data.model.KeyValue;
import com.register.me.model.data.model.ViewActCompProject;
import com.register.me.model.data.model.ViewDetails;
import com.register.me.model.data.repository.CacheRepo;
import com.register.me.model.data.util.Utils;
import com.register.me.view.BaseActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;


public class ViewProductPresenter implements ClientNetworkCall.NetworkCallInterface {
    Context context;
    @Inject
    Constants constants;
    @Inject
    Retrofit retrofit;
    @Inject
    ClientNetworkCall networkCall;
    @Inject
    CacheRepo repo;
    @Inject
    Utils utils;
    int headerCase;
    private ViewDetails.ProductDetails detailList;
    private IViewProduct listener;
    private Resources resources;
    private ArrayList<KeyValue> kv;
    private boolean bid;
    private int projectAssignID;


    public void init(Context context, IViewProduct listener) {
        this.context = context;
        this.listener = listener;
        ((BaseActivity) context).injector().inject(this);
        resources = context.getResources();
        kv = new ArrayList<>();
    }


    public void extractData() {
        ViewDetails.Product product = detailList.getProduct();
        ArrayList<KeyValue> kv = new ArrayList<>();
        kv.add(new KeyValue(context.getResources().getString(R.string.product_number), String.valueOf(product.getProductNumber()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.product_name), product.getProductName(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.product_classification), product.getDeviceClassification(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.is_it_idv), String.valueOf(product.getIvd()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.is_it_life_supporting_life_sustaining), String.valueOf(product.getLifeSupporting()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.any_direct_or_indirect_patients_contacting_components), String.valueOf(product.getPatientContactComponent()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.if_yes_is_it_implant), String.valueOf(product.getIsitAnImplant()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.does_the_product_use_software_firmware), String.valueOf(product.getUseSoftware()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.if_yes_does_it_contains_digital_health_technology), String.valueOf(product.getDigitalHealthTechnology()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.connection_type), product.getConnectionType(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.does_the_product_need_sterlization), String.valueOf(product.getRequiredSterilization()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.sterilization_type), product.getSterilizationType().isEmpty()? " - ":product.getSterilizationType() , null));
        kv.add(new KeyValue(context.getResources().getString(R.string.indications_for_use), product.getIndications(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.intended_population), product.getIntendedPopulation(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.device_type), product.getDeviceType(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.usage_environment), product.getUseEnvironment(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.is_this_a_combination_product), String.valueOf(product.getIsCombinationDevice()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.is_the_product_electrical), String.valueOf(product.getIsElectricalDevice()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.electrical_product_type), product.getElectricalDeviceType().isEmpty()?" - ":product.getElectricalDeviceType(), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.is_the_device_for_pediatric_use), String.valueOf(product.getIsPediatric()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.does_the_device_emit_radiation), String.valueOf(product.getEmitRadiation()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.do_you_have_substance_of_biological_origin_in_your_device), String.valueOf(product.getBiologicalOrigin()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.will_the_device_be_reprocessed_for_resale), String.valueOf(product.getReprocessSUD()), null));
        kv.add(new KeyValue(context.getResources().getString(R.string.product_description), product.getDescription(), null));
        setHeaderContent();
        listener.buildContent(kv);

    }

    public void setHeaderContent() {
        int screen = constants.getviewScreenFrom();
        switch (screen) {
            case 1:
                kv.add(new KeyValue(resources.getString(R.string.product_number), detailList.getProduct().getProductNumber(), null));
                kv.add(new KeyValue(resources.getString(R.string.product_name), detailList.getProduct().getProductName(), null));
                break;
            case 2:
                kv.add(new KeyValue("Product Number", detailList.getProduct().getProductNumber(), null));
                kv.add(new KeyValue("Available to Bid", detailList.getAvalabletoBid(), null));
                kv.add(new KeyValue("Have Submitted Bid", String.valueOf(detailList.getSubmittedBid()), null));
                kv.add(new KeyValue("Bid Status", detailList.getBidStatus(), null));
                break;
            case 3:
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + headerCase);
        }
        listener.buildHeader(kv);

    }

    public void triggerApi(String screenKey) {
        String key = "";
        String id = "";
        String projectID = constants.getProjectID();
        if (projectID != null) {
            key = "projectid";

            id = projectID;
        } else {
            key = "productid";
            id = constants.getProductID();
        }
        if (utils.isOnline(context)) {
            ApiInterface apiInterface = retrofit.create(ApiInterface.class);
            String token = repo.getData(constants.getcacheTokenKey());
            if (screenKey.isEmpty()) {
                networkCall.viewProductOrProject(apiInterface, token, key, id, this);
            } else {
                networkCall.getACDetails(apiInterface, token, id, this);
            }
        } else {
            listener.showErroMessage(context.getResources().getString(R.string.network_alert));
        }
    }


    public String getBoolValue(KeyValue val) {
        String mYES = "YES";
        String mNO = "NO";
        switch (val.getValue()) {
            case "null":
                return "-";
            case "true":
                return mYES;
            case "false":
                return mNO;
            default:
                return val.getValue();
        }
    }

    public StringBuilder getStringFromList(KeyValue val) {
        List<String> subList = val.getSubList();
        StringBuilder builder = new StringBuilder();
        if ((subList != null) && (subList.size() > 0)) {
            Iterator iterator = subList.iterator();
            while (iterator.hasNext()) {
                if (builder.toString().equals("")) {
                    builder.append(iterator.next());
                } else {
                    builder.append("\n").append(iterator.next().toString().trim());
                }
            }

        }
        return builder;
    }

    @Override
    public void onCallSuccess(Object response) {
        listener.dismissProgress();
        bid = false;
        int typeCase = getResponseType(response);
        switch (typeCase) {
            case 1:
                ViewDetails resBody = (ViewDetails) response;
                this.detailList = resBody.getData().getProductDetails();

                if (resBody.getData().getProductDetails().getCrrebiddingdetails() != null) {
                    bid = resBody.getData().getProductDetails().getCrrebiddingdetails().size() != 0;
                }
                extractData();
                break;
            case 2:
                ViewActCompProject resACP = (ViewActCompProject) response;
                ViewActCompProject.Productdetails product = resACP.getData().getProductdetails();
                setProjectAssignID(product.getProjectAssignId());
                String proString = new Gson().toJson(product);
                ViewDetails.Product conversionData = new Gson().fromJson(proString, ViewDetails.Product.class);
                detailList = new ViewDetails.ProductDetails();
                detailList.setProduct(conversionData);
                buildHeader(resACP);
                List<ViewActCompProject.Comment> comments = resACP.getData().getComments();
                    listener.displayComments(comments);
                extractData();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeCase);
        }
    }

    public void setProjectAssignID(int id){
        projectAssignID = id;
    }
    public int getProjectAssignId(){
        return projectAssignID;
    }
    private void buildHeader(ViewActCompProject resACP) {
        kv.clear();
        kv.add(new KeyValue(context.getResources().getString(R.string.product_number), resACP.getData().getProductdetails().getProductNumber(), null));
        kv.add(new KeyValue("Country", resACP.getData().getProjectlocation().getCountry(), null));
        kv.add(new KeyValue("Completion Date", resACP.getData().getProjectOpportunity().getCompletionDate().toString(), null));
        kv.add(new KeyValue("Project Amount", resACP.getData().getCrrebiddingdetails().getProjectAmount(), null));
        String projectStatus = resACP.getData().getProjectOpportunity().getProjectStatus();
        kv.add(new KeyValue("Project Status", projectStatus ==null?" - " : projectStatus, null));
        kv.add(new KeyValue("Project Duration", resACP.getData().getCrrebiddingdetails().getProjectDuration(), null));
        kv.add(new KeyValue("Paid Amount", resACP.getData().getProjectOpportunity().getPaidAmount(), null));
        kv.add(new KeyValue("Bid Amount", resACP.getData().getProjectOpportunity().getBidAmount(), null));
        kv.add(new KeyValue("Balance Amount", resACP.getData().getProjectOpportunity().getBalanceAmount(), null));
        kv.add(new KeyValue("Next Due Amount", resACP.getData().getProjectOpportunity().getNextDueAmount(), null));
        setHeaderContent();
        listener.buildTransactionUI(resACP.getData().getPaymentdetails());
    }

    public View getHeaderView(KeyValue val) {
        View view = LayoutInflater.from(context).inflate(R.layout.key_value_item, null, false);
        TextView key = view.findViewById(R.id.txtKey);
        TextView value = view.findViewById(R.id.txtValue);
        key.setText(val.getKey());
        value.setText(val.getValue());
        return view;
    }

    public View getContentView(KeyValue val) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.item_view_details, null, false);
        TextView title = inflater.findViewById(R.id.txtTitle);
        TextView content = inflater.findViewById(R.id.txtContent);
        title.setText(val.getKey());
        if (val.getValue() != null) {
            String boolVal = "";
            boolVal = getBoolValue(val);
            content.setText(boolVal);
        } else {
            StringBuilder builder = getStringFromList(val);
            if (builder.toString().isEmpty()) {
                content.setText("-");
            } else {
                content.setText(builder.toString());
            }
        }
        return inflater;
    }

    public View getBidView(ViewDetails.Crrebiddingdetail crreItem) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.item_bid, null, false);
        TextView regExpert = inflater.findViewById(R.id.txt_transaction_title);
        TextView sudDate = inflater.findViewById(R.id.txt_sub_Data_val);
        TextView compDate = inflater.findViewById(R.id.txt_trans_amount);
        TextView amount = inflater.findViewById(R.id.txt_trans_date);
        TextView remark = inflater.findViewById(R.id.txt_trans_status);
        TextView status = inflater.findViewById(R.id.txt_status_val);
        regExpert.setText(crreItem.getCrreName());
        sudDate.setText(crreItem.getSubmittedDate());
        compDate.setText(crreItem.getSubmittedDate());
        amount.setText(String.valueOf(crreItem.getAmount()));
        remark.setText(crreItem.getRemarks() == null ? " - " : crreItem.getRemarks());
        status.setText(crreItem.getBiddingStatus());
        return inflater;
    }

    private int getResponseType(Object response) {
        if (response instanceof ViewDetails) {
            return 1;
        } else if (response instanceof ViewActCompProject) {
            return 2;
        }
        return -1;
    }

    @Override
    public void onCallFail(String message) {
        listener.dismissProgress();
        listener.showErroMessage(message);
    }

    @Override
    public void sessionExpired() {
        listener.showErroMessage("Session Expired");
        repo.storeData(constants.getcacheIsLoggedKey(),"false");
        repo.storeData(constants.getCACHE_USER_INFO(),null);
        utils.sessionExpired(context);
    }

    public boolean hasBids() {
        return bid;
    }

    public List<ViewDetails.Crrebiddingdetail> getBidContainerData() {
        return detailList.getCrrebiddingdetails();
    }

    public synchronized View getBidContainerView(int i, ViewActCompProject.Paymentdetail payItem) {
        View inflater = LayoutInflater.from(context).inflate(R.layout.item_transaction, null, false);
        TextView txtTransactionTitle = inflater.findViewById(R.id.txt_transaction_title);
        TextView txtTransid = inflater.findViewById(R.id.txt_transID);
        TextView txtTransAmount = inflater.findViewById(R.id.txt_trans_amount);
        TextView txtTransDate = inflater.findViewById(R.id.txt_trans_date);
        TextView txtTransStatus = inflater.findViewById(R.id.txt_trans_status);
        TextView txtDownload = inflater.findViewById(R.id.txt_download);

        txtTransactionTitle.setText("Transaction " + i);
        txtTransid.setText(payItem.getTransactionID());
        txtTransAmount.setText(payItem.getAmount());
        txtTransDate.setText(payItem.getDate());
        txtTransStatus.setText(payItem.getStatus());
        txtDownload.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://192.168.88.68:8090" + payItem.getUrl()));
            context.startActivity(browserIntent);
        });
        return inflater;
    }

    public interface IViewProduct {

        void buildContent(ArrayList<KeyValue> kv);

        void buildHeader(ArrayList<KeyValue> detailList);

        void showErroMessage(String session_expired);

        void buildTransactionUI(List<ViewActCompProject.Paymentdetail> paymentdetails);

        void displayComments(List<ViewActCompProject.Comment> comments);

        void showProgress();

        void dismissProgress();
    }
}
