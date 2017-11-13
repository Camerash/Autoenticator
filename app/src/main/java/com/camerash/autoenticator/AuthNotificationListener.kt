package com.camerash.autoenticator

import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.support.v4.app.NotificationCompat

/**
 * Created by camerash on 11/13/17.
 * Listener to intercept 2FA authenticate notifications
 */

class AuthNotificationListener : NotificationListenerService() {

    val DUO_SECURITY_PACK_NAME = "com.duosecurity.duomobile"
    val CONFIRM_ACTION_INDEX = 0

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if(sbn == null) return
        if(sbn.packageName == DUO_SECURITY_PACK_NAME) {
            val notification = sbn.notification
            if(NotificationCompat.getActionCount(notification) <= 0) return
            NotificationCompat.getAction(notification, CONFIRM_ACTION_INDEX).actionIntent.send()
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
}