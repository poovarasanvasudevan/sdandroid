package com.caretech.servicefocus.core

import android.content.Context
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible


fun Context.adapter(): FlexibleAdapter<IFlexible<*>> {
    val ga = FlexibleAdapter<IFlexible<*>>(mutableListOf())
    return ga
}