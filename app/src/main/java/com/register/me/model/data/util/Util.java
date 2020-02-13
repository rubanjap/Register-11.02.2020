package com.register.me.model.data.util;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.Window;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.register.me.R;
import com.register.me.model.data.exception.RetrofitException;

import java.util.Calendar;

import io.reactivex.exceptions.CompositeException;

/**
 * Created by Bajic Dusko (www.bajicdusko.com) on 23/03/2017.
 */

public class Util {
    public static String getErrorMessage(Throwable throwable) {
        if (throwable instanceof RetrofitException) {
            return ((RetrofitException) throwable).getRetrofitErrorMessage();
        } else if (throwable instanceof CompositeException) {
            return ((CompositeException) throwable).getExceptions().get(0).getMessage();
        } else {
            return throwable.getMessage();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void showCalendar(Context context, UtilInterface listener){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.calendar_view);
        CalendarView calendarView = dialog.findViewById(R.id.calendar);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, month, dayOfMonth);

                Log.d("Calendar",dayOfMonth+"-"+month+"-"+year);
                Log.d("Calendar",c.getTimeInMillis()+"");
                listener.onDateSet(c.getTimeInMillis());
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public interface UtilInterface{
        void onDateSet(long timeInMillis);
    }
}
