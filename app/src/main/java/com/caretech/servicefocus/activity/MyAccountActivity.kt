package com.caretech.servicefocus.activity

import android.content.Context
import android.view.MenuItem
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class MyAccountActivity : BaseActivity() {
    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "My Accounts"
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun START() {

    }

    override fun STOP() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun layout(): Int = R.layout.activity_my_account

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                android.R.id.home -> onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
