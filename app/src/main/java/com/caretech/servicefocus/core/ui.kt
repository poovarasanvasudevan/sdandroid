package com.caretech.servicefocus.core

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewManager
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import sakout.mehdi.StateViews.StateView


fun Context.badge(text: String, color: Int = Color.WHITE): View {
    return UI {
        relativeLayout {
            textView(text) {
                textSize = dip(14).toFloat()
                textColor = color
            }.lparams(width = wrapContent, height = wrapContent)

            lparams(width = wrapContent, height = wrapContent) {
                padding = dip(4)
            }
        }
    }.view
}

inline fun ViewManager.stateView() = stateView {}

inline fun ViewManager.stateView(init: StateView.() -> Unit): StateView {
    return ankoView({ StateView(it) }, theme = 0, init = init)
}

fun ProgressDialog.dismissOnShow() {
    if(this.isShowing)
        this.dismiss()
}