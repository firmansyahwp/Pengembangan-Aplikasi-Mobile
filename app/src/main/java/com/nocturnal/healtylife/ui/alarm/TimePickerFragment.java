package com.nocturnal.healtylife.ui.alarm;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import com.nocturnal.healtylife.R;
import com.nocturnal.healtylife.ui.alarm.NotificationPublisher;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private TextView alarm_time;

    public TimePickerFragment(TextView alarm_time) {
        this.alarm_time = alarm_time;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, minute,
                true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();
        String shour = String.valueOf(hour);
        String sminute = String.valueOf(minute);

        shour = shour.length() == 1 ? "0" + shour : shour;
        sminute = sminute.length() == 1 ? "0" + sminute : sminute;

        String time = shour + ":" + sminute;

        notification(time);
    }

    private void notification(String time) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "1001")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_description))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.notification_description)))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getActivity(), NotificationPublisher.class);
        intent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        intent.putExtra(NotificationPublisher.NOTIFICATION, builder.build());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault());
        String date = simpleDateFormat.format(c);
        String datetime = date + " " + time;

        try {
            Date mDate = simpleDateFormat1.parse(datetime);
            long timeInMillis = mDate.getTime();
            long estTime = timeInMillis - System.currentTimeMillis();

            if (timeInMillis < System.currentTimeMillis()) {
                Toast.makeText(getActivity(), "Time Must At Least One Minute From Current Time\nDatetime : "+datetime, Toast.LENGTH_SHORT).show();
            } else {
                assert alarmManager != null;
                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + estTime, pendingIntent);
                alarm_time.setText(time);
                Toast.makeText(getActivity(), "Reminder Success", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
