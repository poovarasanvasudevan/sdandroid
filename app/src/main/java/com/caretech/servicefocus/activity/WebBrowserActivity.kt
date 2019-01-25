package com.caretech.servicefocus.activity

import android.content.Context
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.BaseApplication
import com.caretech.servicefocus.core.receiver.WebViewInterface
import org.jetbrains.anko.toast
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class WebBrowserActivity : BaseActivity() {

    lateinit var webView : WebView
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    override fun START() {
        webView.loadUrl("http://10.0.2.2:3000/")
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebViewInterface(this@WebBrowserActivity) , "Android")
        webView.webChromeClient = WebClient()
    }

    class WebClient : WebChromeClient() {
        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            BaseApplication.context.toast(message!!)
            return super.onJsAlert(view, url, message, result)
        }
    }

    override fun STOP() {

    }

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        webView = view().findViewById(R.id.webView)
    }

    override fun layout(): Int = R.layout.activity_web_browser
}
