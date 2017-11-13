package com.camerash.autoenticator

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.support.v4.app.NotificationManagerCompat

/**
 * Created by camerash on 11/8/17.
 * Utils class for SharedPreference, used to store settings and status
 */

class Utils {
    companion object {

        val ENABLE_TOAST_KEY = "ENABLE_TOAST"
        val DUO_SECURITY_PACK_NAME = "com.duosecurity.duomobile"
        val SERVICE_ENABLED_KEY = "SERVICE_ENABLED"

        val CONFIRM_ACTION_INDEX = 0

        var prefs: SharedPreferences? = null

        fun initSharedPreference(c: Context) {
            prefs = PreferenceManager.getDefaultSharedPreferences(c)
        }

        fun putInt(key: String, i: Int) {
            if(prefs !is SharedPreferences) return

            val editor = prefs!!.edit()
            editor.putInt(key, i)
            editor.apply()
        }

        fun getInt(key: String): Int {
            if(prefs !is SharedPreferences) return -1

            return prefs!!.getInt(key, -1)
        }

        fun checkNotificationListenerServicePermission(context: Context): Boolean{
            return NotificationManagerCompat.getEnabledListenerPackages(context).any { it == context.packageName }
        }
    }
}