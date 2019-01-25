package com.caretech.servicefocus.core

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import eu.davidea.flexibleadapter.common.FlexibleItemAnimator
import java.util.jar.Attributes

class SFRecyclerView(context: Context) : RecyclerView(context) {


    init {
        this.itemAnimator = FlexibleItemAnimator()
    }
}