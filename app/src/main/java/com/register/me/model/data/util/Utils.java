package com.register.me.model.data.util;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.register.me.R;
import java.util.Calendar;
import java.util.regex.Pattern;


public class Utils {

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
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.calendar_view);
        final int[] min = new int[1];
        final int[] hour = new int[1];

        CalendarView calendarView = dialog.findViewById(R.id.calendar);
        TimePicker picker = (TimePicker) dialog.findViewById(R.id.timePicker1);
        Button ok = dialog.findViewById(R.id.ok_btn);
        if (call == 0) {

            picker.setVisibility(View.GONE);
            ok.setVisibility(View.GONE);

            calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
                @Override
                public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    Calendar c = Calendar.getInstance();
                    c.set(year, month, dayOfMonth);

                    Log.d("Calendar", dayOfMonth + "-" + month + "-" + year);
                    Log.d("Calendar", c.getTimeInMillis() + "");
                    listener.onDateSet(c.getTimeInMillis());
                    dialog.dismiss();
                }
            });
        } else {
            calendarView.setVisibility(View.GONE);
            hour[0] = picker.getCurrentHour();
            min[0] =picker.getCurrentMinute();
            picker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {


                @Override
                public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                    hour[0] = hourOfDay;
                    min[0] =minute;
                }
            });
            ok.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    listener.onTimeSet(hour[0], min[0]);
                    dialog.dismiss();
                }
            });
        }


        dialog.show();
    }


    /*
    *   case 1 - Registration Success Alert
    *   case 2 - Forgot Password Alert
    *   case 3 - Password Link Alert
    * */
    public void showAlert(Context context, int viewCase,UtilAlertInterface listener){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_view, null);
        dialog.setView(dialogView);
        dialog.setCancelable(false);

        LinearLayout layout_registration_success = dialogView.findViewById(R.id.layout_registration_success);
        LinearLayout layout_forgot_password = dialogView.findViewById(R.id.layout_forgot_password);
        LinearLayout layout_password_link_success = dialogView.findViewById(R.id.layout_password_link_success);
        LinearLayout layout_password_update_success = dialogView.findViewById(R.id.layout_pass_update_success);
        LinearLayout layout_logout = dialogView.findViewById(R.id.layout_logout);
        AlertDialog alertDialog = dialog.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        switch(viewCase){
            case 1:
                layout_registration_success.setVisibility(View.VISIBLE);
                Button ok = dialogView.findViewById(R.id.btn_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        ((Activity)context).finish();
                    }
                });
                break;
            case 2:
                layout_forgot_password.setVisibility(View.VISIBLE);
                EditText edtEmailAddress = dialogView.findViewById(R.id.edt_emailAddress);
                Button btnEmail = dialogView.findViewById(R.id.btn_email);
                btnEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String val = edtEmailAddress.getText().toString();
                        if(val.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(val).matches()){
                            listener.alertResponse("$ERROR$ Please enter a valid email");
                        }else {
                            listener.alertResponse("$EMAIL$"+val);
                        }
                        alertDialog.dismiss();
                    }
                });

                break;
            case 3:
                layout_password_link_success.setVisibility(View.VISIBLE);
                Button btn_password_ok = dialogView.findViewById(R.id.btn_password_ok);
                btn_password_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;
            case 4:
                layout_password_update_success.setVisibility(View.VISIBLE);
                Button btn_ok = dialogView.findViewById(R.id.btn_pass_ok);
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.alertResponse("$Success$");
                        alertDialog.dismiss();
                    }
                });
                break;
            case 5:
                layout_logout.setVisibility(View.VISIBLE);
                Button btn_pass_yes = dialogView.findViewById(R.id.btn_pass_yes);
                Button btn_pass_cancel = dialogView.findViewById(R.id.btn_pass_cancel);
                btn_pass_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        listener.alertResponse("$LOGOUT");
                    }
                });
                btn_pass_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;

        }


        alertDialog.show();
    }

    public interface UtilDateTimeInterface {
        void onDateSet(long timeInMillis);

        void onTimeSet(Integer currentHour, Integer currentMinute);
    }

    public interface UtilAlertInterface {
        void alertResponse(String success);
    }
}
