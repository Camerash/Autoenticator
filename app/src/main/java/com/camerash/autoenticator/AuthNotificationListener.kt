package com.camerash.autoenticator

import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.support.v4.app.NotificationCompat
import android.widget.Toast



/**
 * Created by camerash on 11/13/17.
 * Listener to intercept 2FA authenticate notifications
 */

class AuthNotificationListener : NotificationListenerService() {

    val DUO_SECURITY_PACK_NAME = "com.duosecurity.duomobile"
    val SERVICE_ENABLED_KEY = "SERVICE_ENABLED"
    val CONFIRM_ACTION_INDEX = 0
    var authencating = false

    override fun onBind(intent: Intent): IBinder? {
        Utils.initSharedPreference(applicationContext)
        return super.onBind(intent)
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        // check notification for null
        if(sbn == null) return

        // check if user enabled service
        // we need to do this since we cannot directly start / stop a service that extends NotificationListenerService
        if(Utils.getInt(SERVICE_ENABLED_KEY) <= 0) return


        if(sbn.packageName == DUO_SECURITY_PACK_NAME) {
            // get notification
            val notification = sbn.notification

            // check for available actions
            if(NotificationCompat.getActionCount(notification) <= 0) return

            // approve 2FA!
            NotificationCompat.getAction(notification, CONFIRM_ACTION_INDEX).actionIntent.send()

            val handler = Handler(Looper.getMainLooper())
            val postHandler = Handler()
            handler.post({
                if(authencating) {
                    Toast.makeText(this@AuthNotificationListener.applicationContext, getString(R.string.autoenticating), Toast.LENGTH_SHORT).show()
                }
                authencating = true
                postHandler.postDelayed({authencating = false},3000)
            })
        }
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
    }
}