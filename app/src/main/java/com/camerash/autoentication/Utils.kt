package com.camerash.autoentication

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by camerash on 11/8/17.
 * Utils class for SharedPreference, used to store settings and status
 */

class Utils {
    companion object {
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
    }
}