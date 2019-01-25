package com.caretech.servicefocus.core

import com.flipkart.android.proteus.Proteus
import com.flipkart.android.proteus.ProteusBuilder
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory
import java.util.*

class ProteusManager {

    val proteus: Proteus
    private val listeners = HashSet<Listener>()

    init {
        proteus = ProteusBuilder()
                .build()

        ProteusTypeAdapterFactory.PROTEUS_INSTANCE_HOLDER.proteus = proteus

    }


    fun addListener(listener: Listener) {
        listeners.add(listener)
    }

    fun removeListener(listener: Listener) {
        listeners.remove(listener)
    }

    private fun broadcast(e: Exception?) {
        if (e == null) {
            notifySuccess()
        } else {
            notifyError(e)
        }
    }

    private fun notifySuccess() {
        for (listener in listeners) {
            listener.onLoad()
        }
    }

    private fun notifyError(e: Exception) {
        for (listener in listeners) {
            listener.onError(e)
        }
    }


    interface Listener {
        fun onLoad()
        fun onError(e: Exception)
    }
}