package com.caretech.servicefocus.core.receiver

import android.content.Context
import android.webkit.JavascriptInterface
import com.mcxiaoke.koi.ext.toast

class WebViewInterface(val context : Context) {

    @JavascriptInterface
    fun alert(message : String) {
        context.toast(message)
    }
}