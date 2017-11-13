package com.camerash.autoenticator

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*

/**
 * Created by camerash on 11/13/17.
 * Settings activity
 */
class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        initToastSetting()
        initButtons()
    }

    fun initToastSetting() {
        toast_switch.isChecked = Utils.getInt(Utils.ENABLE_TOAST_KEY) > 0
        toast_switch.setOnClickListener {
            Utils.putInt(Utils.ENABLE_TOAST_KEY, if(toast_switch.isChecked) 1 else 0)
        }

        toast_layout.setOnClickListener {
            Toast.makeText(this, getString(R.string.autoenticating), Toast.LENGTH_SHORT).show()
        }
    }

    fun initButtons() {
        manage_system_noti_permission.setOnClickListener {
            val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }
    }
}