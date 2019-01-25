package com.caretech.servicefocus.core

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.graphics.Color
import androidx.multidex.MultiDex
import com.caretech.servicefocus.R
import com.caretech.servicefocus.core.classes.Notes
import com.jayway.jsonpath.Configuration
import com.jayway.jsonpath.Option
import com.jayway.jsonpath.spi.json.GsonJsonProvider
import com.jayway.jsonpath.spi.json.JsonProvider
import com.jayway.jsonpath.spi.mapper.GsonMappingProvider
import com.jayway.jsonpath.spi.mapper.MappingProvider
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import com.parse.Parse
import com.parse.ParseInstallation
import com.parse.ParseObject
import sakout.mehdi.StateViews.StateViewsBuilder
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import java.util.*


class BaseApplication : Application() {

    companion object {
        lateinit var context: Context
            private set

    }

    @SuppressLint("ResourceAsColor")
    override fun onCreate() {
        super.onCreate()

        Parse.enableLocalDatastore(this)
        ParseObject.registerSubclass(Notes::class.java)
        Parse.initialize(Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.app_server_appid))
                .clientKey(getString(R.string.app_server_clientkey))
                .server(getString(R.string.app_server_url))
                .enableLocalDataStore()
                .build()
        )
        ParseInstallation.getCurrentInstallation().saveInBackground()


        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/roboto.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
        MultiDex.install(this);
        context = this.applicationContext
        StateViewsBuilder
                .init(this)
                .addState("NO_NETWORK",
                        "No Connection",
                        "Error retrieving information from server.",
                        null,
                        "Retry"
                )

                .addState("NO_NOTES",
                        "No Notes Available",
                        "No Notes Available for you to show.",
                        null,
                        "Create"
                )

                .addState("NO_NOTIFICATION",
                        "No Notification Available",
                        "No Notification Available for you to show.",
                        applicationContext.icon(GoogleMaterial.Icon.gmd_notifications , color(R.color.colorPrimary) ,70),
                        null
                )

                .addState("OOPS_MESSAGE",
                        "Something Went Wrong",
                        "Server Not Responding Please try later.",
                        null,
                        null
                )

                .setButtonBackgroundColor(color(R.color.colorPrimary))
                .setButtonTextColor(Color.parseColor("#FFFFFF"))
                .setIconColor(color(R.color.colorPrimary))
                .setIconSize(200)

        Configuration.setDefaults(object : Configuration.Defaults {

            private val jsonProvider = GsonJsonProvider()
            private val mappingProvider = GsonMappingProvider()

            override fun jsonProvider(): JsonProvider {
                return jsonProvider
            }

            override fun mappingProvider(): MappingProvider {
                return mappingProvider
            }

            override fun options(): Set<Option> {
                return EnumSet.noneOf(Option::class.java)
            }
        })



    }
}