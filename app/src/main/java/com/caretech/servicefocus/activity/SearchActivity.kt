package com.caretech.servicefocus.activity

import android.content.Context
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class SearchActivity : BaseActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun COMMON() {

    }

    override fun START() {

    }

    override fun STOP() {
    }

    override fun layout(): Int = R.layout.activity_search


    override fun onBackPressed() {
        super.onBackPressed()
    }
}
