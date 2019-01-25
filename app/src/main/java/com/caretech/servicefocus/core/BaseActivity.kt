package com.caretech.servicefocus.core

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.caretech.servicefocus.R
import com.ferfalk.simplesearchview.SimpleSearchView
import com.google.android.material.appbar.AppBarLayout
import com.mcxiaoke.koi.ext.find
import com.mcxiaoke.koi.ext.getApp
import com.mcxiaoke.koi.ext.isConnected
import com.mcxiaoke.koi.ext.toast
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import sakout.mehdi.StateViews.StateView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper


abstract class BaseActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    fun application(): Application = this.getApp()
    fun recyclerAdapter(): FlexibleAdapter<IFlexible<*>> = adapter()

    lateinit var inflatedView: View
    lateinit var rootView: RelativeLayout
    lateinit var stateView: StateView
    lateinit var toolbar: Toolbar
    lateinit var appBar: AppBarLayout
    lateinit var searchView: SimpleSearchView
    lateinit var openState : StateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_view)

        rootView = find(R.id.contentView)
        stateView = find(R.id.stateView)
        toolbar = find(R.id.toolBar)
        searchView = find(R.id.searchView)
        openState = find(R.id.outerState)
        appBar = find(R.id.appBar)
        openState().hideStates()


        inflatedView = layoutInflater.inflate(layout(), null, false)
        rootView.addView(inflatedView)



        stateView.setOnStateButtonClicked {
            DO_OPERTATION()
        }

        COMMON()
        DO_OPERTATION()
    }



    fun DO_OPERTATION() {
        if (isConnected()) {
            stateView.hideStates()
            START()
        } else {
            stateView.displayState("NO_NETWORK")
            INTERNET_UNAVAILABLE()
        }
    }

    fun openState() : StateView = openState
    fun toolBarView(): Toolbar = toolbar
    fun searchView(): SimpleSearchView = searchView
    fun view(): View = inflatedView
    fun appBarView(): View = appBar

    public override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    public override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    abstract fun START()
    abstract fun STOP()
    abstract fun COMMON()

    fun INTERNET_UNAVAILABLE() {
        toast("Internet Unavailable")
    }

    abstract fun layout(): Int

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun onConnectionEvent(event: ConnectionEvent) {
        DO_OPERTATION()
    }
}