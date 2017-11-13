package com.camerash.autoenticator

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_about.*


/**
 * Created by camerash on 11/13/17.
 * About activity
 */
class AboutActivity : AppCompatActivity() {

    var firstStart = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        firstStart = Utils.getInt(Utils.FIRST_START_KEY) == -1

        initViews()
        setupButtons()
    }

    fun initViews() {
        security_info.movementMethod = LinkMovementMethod.getInstance()
        if(firstStart) {
            next.visibility = View.VISIBLE
            next.setOnClickListener {
                Utils.putInt(Utils.FIRST_START_KEY, 1)
                finish()
            }
        }
    }

    fun setupButtons() {
        license.setOnClickListener {
            val view = LayoutInflater.from(this).inflate(R.layout.webview_dialog, null) as WebView
            view.loadUrl("file:///android_asset/license.html")
            AlertDialog.Builder(this)
                    .setView(view)
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
        }
    }

    override fun onBackPressed() {
        if(firstStart) {
            next.callOnClick()
        } else {
            super.onBackPressed()
        }
    }
}