package com.caretech.servicefocus.core.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.caretech.servicefocus.core.ConnectionEvent
import com.mcxiaoke.koi.ext.isConnected
import org.greenrobot.eventbus.EventBus

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (context.isConnected()) {
            EventBus.getDefault().post(ConnectionEvent(true))
        } else {
            EventBus.getDefault().post(ConnectionEvent(false))
        }
    }
}