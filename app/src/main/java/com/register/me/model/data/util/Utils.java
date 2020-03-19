package com.register.me.model.data.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.register.me.R;
import com.register.me.view.activity.LoginActivity;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Objects;


public class Utils {

    private String commentTopic;

    public boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showCalendar(Context context, UtilDateTimeInterface listener, int call) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.calendar_view);
        final int[] min = new int[1];
        final int[] hour = new int[1];

        CalendarView calendarView = dialog.findViewById(R.id.calendar);
        TimePicker picker = (TimePicker) dialog.findViewById(R.id.timePicker1);
        Button ok = dialog.findViewById(R.id.ok_btn);
        if (call == 0) {

            picker.setVisibility(View.GONE);
            ok.setVisibility(View.GONE);

            calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);

                Log.d("Calendar", dayOfMonth + "-" + month + "-" + year);
                Log.d("Calendar", c.getTimeInMillis() + "");
                listener.onDateSet(c.getTimeInMillis());
                dialog.dismiss();
            });
        } else {
            calendarView.setVisibility(View.GONE);
            hour[0] = picker.getCurrentHour();
            min[0] = picker.getCurrentMinute();
            picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {


                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    hour[0] = hourOfDay;
                    min[0] = minute;
                }
            });
            ok.setOnClickListener(v -> {
                listener.onTimeSet(hour[0], min[0]);
                dialog.dismiss();
            });
        }


        dialog.show();
    }

    /*
     *   case 1 - Registration Success Alert
     *   case 2 - Forgot Password Alert
     *   case 3 - Password Link Alert
     *   case 4 - Password update
     *   case 5 - Logout
     *   case 6 - Update User
     *   case 7 - Direct Assign
     *   case 8 - Cancel Project
     * */
    public void showAlert(Context context, int viewCase, UtilAlertInterface listener) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_view, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        LinearLayout layoutRegistrationSuccess = dialogView.findViewById(R.id.layout_registration_success);
        LinearLayout layoutForgotPassword = dialogView.findViewById(R.id.layout_forgot_password);
        LinearLayout layoutPasswordLinkSuccess = dialogView.findViewById(R.id.layout_password_link_success);
        LinearLayout layoutPasswordUpdateSuccess = dialogView.findViewById(R.id.layout_pass_update_success);
        LinearLayout layoutLogout = dialogView.findViewById(R.id.layout_logout);
        LinearLayout layoutUpdate = dialogView.findViewById(R.id.layout_user_update);
        LinearLayout layoutDirectAssign = dialogView.findViewById(R.id.layout_direct_assign);
        LinearLayout layoutCancelProject = dialogView.findViewById(R.id.layout_cancel_project);
        LinearLayout layoutRequestRegion = dialogView.findViewById(R.id.layout_request_region);
        LinearLayout layoutComments = dialogView.findViewById(R.id.layout_comments);
        AlertDialog alertDialog = dialog.create();
        Objects.requireNonNull(alertDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        switch (viewCase) {
            case 1:
                layoutRegistrationSuccess.setVisibility(View.VISIBLE);
                Button ok = dialogView.findViewById(R.id.btn_ok);
                ok.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    ((Activity) context).finish();
                });
                break;
            case 2:
                layoutForgotPassword.setVisibility(View.VISIBLE);
                EditText edtEmailAddress = dialogView.findViewById(R.id.edt_emailAddress);
                Button btnEmail = dialogView.findViewById(R.id.btn_email);
                Button btnForCancel = dialogView.findViewById(R.id.btn_fCancel);
                btnEmail.setOnClickListener(v -> {
                    String val = edtEmailAddress.getText().toString();
                    if (val.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(val).matches()) {
                        listener.alertResponse("$ERROR$ Please enter a valid email");
                    } else {
                        listener.alertResponse("$EMAIL$" + val);
                        alertDialog.dismiss(); }

                });
                btnForCancel.setOnClickListener(view -> alertDialog.dismiss());

                break;
            case 3:
                layoutPasswordLinkSuccess.setVisibility(View.VISIBLE);
                Button btnPasswordOk = dialogView.findViewById(R.id.btn_password_ok);
                btnPasswordOk.setOnClickListener(v -> alertDialog.dismiss());
                break;
            case 4:
                layoutPasswordUpdateSuccess.setVisibility(View.VISIBLE);
                Button btnOk = dialogView.findViewById(R.id.btn_pass_ok);
                btnOk.setOnClickListener(v -> {
                    listener.alertResponse("$Success$");
                    alertDialog.dismiss();
                });
                break;
            case 5:
                layoutLogout.setVisibility(View.VISIBLE);
                Button btnPassYes = dialogView.findViewById(R.id.btn_pass_yes);
                Button btnPassCancel = dialogView.findViewById(R.id.btn_pass_cancel);
                btnPassYes.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    listener.alertResponse("$LOGOUT");
                });
                btnPassCancel.setOnClickListener(v -> alertDialog.dismiss());
                break;
            case 6:
                layoutUpdate.setVisibility(View.VISIBLE);
                Button btnUserOk = dialogView.findViewById(R.id.btn_user_ok);
                btnUserOk.setOnClickListener(v -> {
                    listener.alertResponse("$SUCCESS$");
                    alertDialog.dismiss();
                });
                break;
            case 7:
                layoutDirectAssign.setVisibility(View.VISIBLE);
                Button btnAssignYes = dialogView.findViewById(R.id.btn_assign_yes);
                Button btnAssignCancel = dialogView.findViewById(R.id.btn_assign_cancel);
                btnAssignYes.setOnClickListener(v -> {
                    alertDialog.dismiss();
                    listener.alertResponse("$OK$");
                });
                btnAssignCancel.setOnClickListener(v -> alertDialog.dismiss());
                break;
            case 8:
                layoutCancelProject.setVisibility(View.VISIBLE);
                Button btnSubmit = dialogView.findViewById(R.id.btn_submit);
                Button btnCancel = dialogView.findViewById(R.id.btn_cancel);
                EditText edtCancel = dialogView.findViewById(R.id.edt_reason);
                btnCancel.setOnClickListener(view -> alertDialog.dismiss());
                btnSubmit.setOnClickListener(view -> {
                    String reason = edtCancel.getText().toString();
                    if (!reason.isEmpty()) {
                        alertDialog.dismiss();
                        listener.alertResponse("$SUCCESS$" + reason);
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            case 9:
                layoutRequestRegion.setVisibility(View.VISIBLE);
                Button btnRegSubmit = dialogView.findViewById(R.id.btn_reg_submit);
                Button btnRegCancel = dialogView.findViewById(R.id.btn_reg_cancel);
                EditText edtRegion = dialogView.findViewById(R.id.edt_region);
                EditText edtDescription = dialogView.findViewById(R.id.edt_description);
                btnRegCancel.setOnClickListener(view -> alertDialog.dismiss());
                btnRegSubmit.setOnClickListener(view -> {
                    String region = edtRegion.getText().toString().trim();
                    String description = edtDescription.getText().toString().trim();
                    if (!TextUtils.isEmpty(region) && !TextUtils.isEmpty(description)) {
                        alertDialog.dismiss();
                        listener.alertResponse(region+":"+description);
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            case 10:
                layoutComments.setVisibility(View.VISIBLE);
                EditText edtTopic = dialogView.findViewById(R.id.edt_topic);
                EditText edtComment = dialogView.findViewById(R.id.edt_comment);
                Button btnSubmitComment = dialogView.findViewById(R.id.btn_submit_comment);
                Button btnCancelComment = dialogView.findViewById(R.id.btn_cancel_comment);
                if(getCommentTopic()!=null &&!getCommentTopic().isEmpty()){
                    edtTopic.setText(getCommentTopic());
                    edtTopic.setFocusable(false);
                }
                btnCancelComment.setOnClickListener(view -> alertDialog.dismiss());
                btnSubmitComment.setOnClickListener(view -> {
                    String topic = edtTopic.getText().toString().trim();
                    String comment = edtComment.getText().toString().trim();
                    if (!TextUtils.isEmpty(topic) && !TextUtils.isEmpty(comment)) {
                        alertDialog.dismiss();
                        listener.alertResponse(topic+":"+comment);
                    } else {
                        listener.alertResponse("$ALERT$");
                    }
                });
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewCase);
        }


        alertDialog.show();
    }

    public int getInputType(int inputType) {
        switch (inputType) {
            case 1:
                return InputType.TYPE_CLASS_TEXT;
            case 2:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case 3:
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            case 4:
                return InputType.TYPE_CLASS_NUMBER;
            default:
                return inputType;
        }
    }

    public void sessionExpired(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    public void setCommentTopic(String commentTopic) {
        this.commentTopic=commentTopic;
    }

    public String getCommentTopic() {
        return commentTopic;
    }

    public interface UtilDateTimeInterface {
        void onDateSet(long timeInMillis);

        void onTimeSet(Integer currentHour, Integer currentMinute);
    }

    public interface UtilAlertInterface {
        void alertResponse(String success);
    }
}
