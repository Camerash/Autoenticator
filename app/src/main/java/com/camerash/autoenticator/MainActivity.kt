package com.camerash.autoenticator

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.view.Menu
import android.view.MenuItem
import com.transitionseverywhere.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by camerash on 11/8/17.
 * Main activity
 */

class MainActivity : AppCompatActivity() {

    var enabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        initUtils()
        initButton()
        checkFirstStartUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
            R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        checkNotiPermission()
    }

    fun initUtils() {
        Utils.initSharedPreference(this)
    }

    fun initButton() {
        enabled = Utils.getInt(Utils.SERVICE_ENABLED_KEY) > 0 // 1 for enabled, 0 for disabled, -1 for new user

        button.shadowHeight = resources.getDimensionPixelSize(R.dimen.shadow_height)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) button.stateListAnimator = null // Necessary to remove shadow bug
        button.setOnClickListener {
            enabled = !enabled
            Utils.putInt(Utils.SERVICE_ENABLED_KEY, if(enabled) 1 else 0)
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

        animateText()
    }

    fun animateText(){
        TransitionManager.beginDelayedTransition(constraintLayout)

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        if(enabled) {
            constraintSet.addToVerticalChain(urlTextView.id, infoTextView.id, -1)
            constraintSet.centerVertically(infoTextView.id, imageView.id, ConstraintSet.BOTTOM, 0, button.id, ConstraintSet.TOP, 0, 0.5f)
        } else {
            constraintSet.centerVertically(infoTextView.id, button.id, ConstraintSet.TOP, 0, button.id, ConstraintSet.BOTTOM, 0, 0.5f)
        }

        constraintSet.applyTo(constraintLayout)
    }

    fun checkNotiPermission() {
        if(!Utils.checkNotificationListenerServicePermission(this)) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle(getString(R.string.alert_title))
            builder.setMessage(getString(R.string.alert_message))
            builder.setPositiveButton("OK", { _, _ ->
                startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"))
            })
            builder.setNegativeButton("Cancel", { _, _ ->
                finish()
            })
            builder.setCancelable(false)

            val dialog = builder.create()
            dialog.show()
        }
    }

    fun checkFirstStartUp() {
        if(Utils.getInt(Utils.FIRST_START_KEY) == -1) {
            Utils.putInt(Utils.ENABLE_TOAST_KEY, 1)
            startActivity(Intent(this, AboutActivity::class.java))
        }
    }

}
