package com.camerash.autoentication

import android.os.Build
import android.os.Bundle
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val SERVICE_ENABLED_KEY = "SERVICE_ENABLED"
    var enabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initUtils()
        initButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    fun initUtils() {
        Utils.initSharedPreference(this)
    }

    fun initButton() {
        enabled = Utils.getInt(SERVICE_ENABLED_KEY) > 0 // 1 for enabled, 0 for disabled, -1 for new user

        button.shadowHeight = resources.getDimensionPixelSize(R.dimen.shadow_height)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) button.stateListAnimator = null // Necessary to remove shadow bug
        button.setOnClickListener {
            enabled = !enabled
            Utils.putInt(SERVICE_ENABLED_KEY, if(enabled) 1 else 0)
            redrawUI()
        }
        redrawUI()
    }

    fun redrawUI() {
        button.buttonColor = ContextCompat.getColor(this,  if(enabled) R.color.alizarin else R.color.emerald)
        button.text = getString(if(enabled) R.string.disable else R.string.enable)
        val avd = AnimatedVectorDrawableCompat.create(this, if(enabled) R.drawable.unlock_avd else R.drawable.lock_avd)
        imageView.setImageDrawable(avd)
        avd?.start()
    }

}
