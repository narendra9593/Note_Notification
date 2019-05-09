package com.example.note_notification

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.note_notification.AppConstant.DONE_ACTION
import com.example.note_notification.AppConstant.SNOOZE_ACTION
import java.util.*


class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        val action = intent.action

        var notifyId = intent.getIntExtra(DONE_ACTION, 0)

        if (DONE_ACTION == action) {
            manager.cancel(notifyId)
        } else if (SNOOZE_ACTION == action) {
            Log.d("onReceive","======>>> Snooze me")
//            setNotification(notifyId, context)
        }
    }

//    @TargetApi(Build.VERSION_CODES.O)
//    fun setNotification(timeInMilliSeconds: Long, activity: Context) {
//        //------------  alarm settings start  -----------------//
//        NotificationHelper(activity).notify()
//        if (timeInMilliSeconds > 0) {
//            val alarmManager = activity.getSystemService(Activity.ALARM_SERVICE)
//                    as AlarmManager
//            val alarmIntent = Intent(activity.applicationContext, NotificationReceiver::class.java)
//
//            alarmIntent.putExtra("reason", "notification")
//            alarmIntent.putExtra("timestamp", timeInMilliSeconds)
//
//            val calendar = Calendar.getInstance()
//            calendar.timeInMillis = timeInMilliSeconds
//
//            val pendingIntent = PendingIntent.getBroadcast(activity, 0,
//                    alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT)
//            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
//
//        }
//        //------------ end of alarm settings  -----------------//
//
//    }
}
