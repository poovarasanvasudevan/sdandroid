package com.caretech.servicefocus.activity

import android.content.Context
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class TicketsActivity : BaseActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun START() {

    }

    override fun STOP() {

    }

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Tickets"

        //GoogleMaterial.Icon.gmd_chat

    }

    override fun layout(): Int = R.layout.activity_tickets

}
