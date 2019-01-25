package com.caretech.servicefocus

import android.content.Intent
import android.content.IntentFilter
import android.os.Handler
import android.view.View
import com.caretech.servicefocus.activity.LoginActivity
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.receiver.NetworkChangeReceiver
import com.parse.ParseInstallation


class MainActivity : BaseActivity() {

    override fun COMMON() {
        appBarView().visibility = View.GONE

        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        this.registerReceiver(NetworkChangeReceiver(), intentFilter)

        ParseInstallation.getCurrentInstallation().saveInBackground()
    }

    override fun START() {
        Handler().postDelayed({
//          if (ParseUser.getCurrentUser() != null && ParseUser.getCurrentUser().isAuthenticated && ParseUser.getCurrentSessionToken() !=null) {
//                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
//                finish()
//            } else {
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finish()
          //  }
        }, 1000)
    }

    override fun STOP() {
    }

    override fun layout(): Int = R.layout.activity_main

}
