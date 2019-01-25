package com.caretech.servicefocus.activity

import android.content.Context
import android.view.Menu
import android.view.MenuItem
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class NotificationActivity : BaseActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Notifications"
    }

    override fun START() {
        openState().displayState("NO_NOTIFICATION")
    }

    override fun STOP() {

    }

    override fun layout(): Int = R.layout.activity_notification
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }
}
