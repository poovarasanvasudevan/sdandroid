package com.caretech.servicefocus.activity

import android.content.Context
import android.view.Menu
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class VacationActivity : BaseActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun COMMON() {

    }

    override fun START() {

    }

    override fun STOP() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }


    override fun layout(): Int = R.layout.activity_vacation
}
