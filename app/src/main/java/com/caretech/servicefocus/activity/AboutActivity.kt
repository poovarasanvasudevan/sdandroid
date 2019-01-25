package com.caretech.servicefocus.activity

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.MenuItem
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.BaseActivity
import com.caretech.servicefocus.core.icon
import com.caretech.servicefocus.core.readJSONFromAsset
import com.google.gson.JsonParser
import com.mcxiaoke.koi.ext.find
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import org.jetbrains.anko.browse
import org.json.JSONObject
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

class AboutActivity : BaseActivity() {

    lateinit var aboutPageBuilder: AboutPage

    override fun START() {

        val versionElement = Element()
        versionElement.title = "Version 1.0"

        aboutPageBuilder = AboutPage(this)
                .isRTL(false)
                .addGroup("Connect with us")

//        ParseConfig.getInBackground { config, e ->
//            if (e == null) {
                val aboutItems = JSONObject(readJSONFromAsset("about"))
                if (aboutItems != null) {
                    val aboutgson = JsonParser().parse(aboutItems.toString()).asJsonObject

                    if(aboutgson.has("description")) {
                        aboutPageBuilder.setDescription(aboutgson.get("description").asString)
                    }

                    if(aboutgson.has("logo")) {

                        Glide.with(this@AboutActivity)
                                .load(aboutgson.get("logo").asString)
                                .into(object : SimpleTarget<Drawable>() {
                                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                                        aboutPageBuilder.setImage(resource)
                                    }
                                })
                    }


                    val items = aboutgson.get("items").asJsonArray
                    (0 until items.size()).forEach {
                        val cJson = items.get(it).asJsonObject
                        if(cJson.has("title") &&
                                cJson.has("link") &&
                                cJson.get("title").asString.isNotEmpty() &&
                                cJson.get("link").asString.isNotEmpty()) {
                            val im = Element()
                            im.title = cJson.get("title").asString
                            if(cJson.has("icon")) {
                                val iconcolor = if(cJson.has("iconcolor")) Color.parseColor(cJson.get("iconcolor").asString) else Color.DKGRAY
                                im.iconDrawable = icon(cJson.get("icon").asString, iconcolor ,14)
                            }

                            im.setOnClickListener {
                                browse(cJson.get("link").asString)
                            }
                            aboutPageBuilder.addItem(im)
                        }
                    }
                }

                view().find<RelativeLayout>(R.id.fLayout)
                        .addView(aboutPageBuilder.create())
            }
//        }
//    }

    override fun STOP() {

    }

    override fun COMMON() {
        setSupportActionBar(toolBarView())
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "About Us"
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    override fun layout(): Int = R.layout.activity_about

}
