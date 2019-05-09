package com.example.note_notification


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import com.example.note_notification.AppConstant.DONE_ACTION
import com.example.note_notification.AppConstant.SNOOZE_ACTION


/**
 * Helper class to manage notification channels, and create notifications.
 */
@RequiresApi(Build.VERSION_CODES.O)
internal class NotificationHelper(context: Context) : ContextWrapper(context) {



    private lateinit var pendingIntentTaskDone: PendingIntent
    private lateinit var pendingIntentSnooze: PendingIntent
    companion object {
        const val LOW_CHANNEL = "Low"
        const val MEDIUM_CHANNEL = "Medium"
        const val HIGH_CHANNEL = "High"
    }

    private val mNotificationManager: NotificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    /**
     * Registers notification channels, which can be used later by individual notifications.
     */
    init {

        // Low Priority
        val lowChannel = NotificationChannel(
                LOW_CHANNEL,
                getString(R.string.low_priority_notes),
                NotificationManager.IMPORTANCE_LOW)

        // Configure the channel's initial settings
        lowChannel.lightColor = Color.GREEN
        lowChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 500, 200, 500)
        lowChannel.setShowBadge(false)

        // Submit the notification channel object to the notification manager
        mNotificationManager.createNotificationChannel(lowChannel)

        // Medium Priority
        val mediumChannel = NotificationChannel(
                MEDIUM_CHANNEL,
                getString(R.string.medium_priority_notes),
                NotificationManager.IMPORTANCE_MIN)
        mediumChannel.lightColor = Color.BLUE
        mediumChannel.setShowBadge(true)
        mNotificationManager.createNotificationChannel(mediumChannel)

        // High Priority
        val highChannel = NotificationChannel(
                HIGH_CHANNEL,
                getString(R.string.high_priority_notes),
                NotificationManager.IMPORTANCE_HIGH)
        highChannel.lightColor = Color.RED
        highChannel.setShowBadge(true)
        mNotificationManager.createNotificationChannel(highChannel)
    }

    /**
     * Get a follow/un-follow notification
     *
     * Provide the builder rather than the notification it's self as useful for making
     * notification changes.
     * @param body  the body text for the notification
     * *
     * @return A Notification.Builder configured with the selected channel and details
     */
    fun getNotificationLowPriority(body: String): Notification.Builder {
        return Notification.Builder(applicationContext, LOW_CHANNEL)
                .setContentTitle("")
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .setAutoCancel(true)
    }

    /****
     *
     */
    fun getNotificationMediumPriority(id: Int, body: String): Notification.Builder {
        setDataTaskDone(id)
        setDataSnooze(id)
        return Notification.Builder(applicationContext, MEDIUM_CHANNEL)
                .setContentTitle("")
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .setOngoing(true)
                .setAutoCancel(false)
                .addAction(R.drawable.ic_launcher_foreground, "Snooze", pendingIntentSnooze)
                .addAction(R.drawable.ic_launcher_foreground, "Task Done", pendingIntentTaskDone)
                .setContentIntent(pendingIntentTaskDone)
                .setContentIntent(pendingIntentSnooze)
    }


    /**
     * Get a direct message notification
     *
     * Provide the builder rather than the notification it's self as useful for making
     * notification changes.

     * @param title Title for notification.
     * *
     * @param body  Message for notification.
     * *
     * @return A Notification.Builder configured with the selected channel and details
     */
    fun getNotificationHighPriority(id: Int, body: String)
            : Notification.Builder {
        setDataTaskDone(id)
        setDataSnooze(id)
        return Notification.Builder(applicationContext, HIGH_CHANNEL)
                .setContentTitle("")
                .setContentText(body)
                .setSmallIcon(smallIcon)
                .addAction(R.drawable.ic_launcher_foreground, "Snooze", pendingIntentSnooze)
                .addAction(R.drawable.ic_launcher_foreground, "Task Done", pendingIntentTaskDone)
                .setOngoing(true)
                .setAutoCancel(false)
                .setContentIntent(pendingIntentTaskDone)
                .setContentIntent(pendingIntentSnooze)
    }


    /**
     * Send a notification.
     *
     * @param id           The ID of the notification
     * *
     * @param notification The notification object
     */
    fun notify(id: Int, notification: Notification.Builder) {

        mNotificationManager.notify(id, notification.build())
    }

    /**
     * Get the small icon for this app

     * @return The small icon resource id
     */
    private val smallIcon: Int
        get() = android.R.drawable.stat_notify_chat


    private fun setDataTaskDone(id: Int) {
        val num = System.currentTimeMillis().toInt()
        val doneIntent = Intent(this, NotificationReceiver::class.java)
        doneIntent.putExtra(DONE_ACTION, id)
        doneIntent.setAction(DONE_ACTION);
        pendingIntentTaskDone = PendingIntent.getBroadcast(this, num,
                doneIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun setDataSnooze(id: Int) {
        val num = System.currentTimeMillis().toInt()
        val snoozeIntent = Intent(this, NotificationReceiver::class.java)
        snoozeIntent.putExtra(SNOOZE_ACTION, id)
        snoozeIntent.setAction(SNOOZE_ACTION);
        pendingIntentSnooze = PendingIntent.getBroadcast(this, num,
                snoozeIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

}
