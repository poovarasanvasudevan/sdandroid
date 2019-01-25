package com.caretech.servicefocus.core

import android.content.Context
import com.flipkart.android.proteus.gson.ProteusTypeAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlin.reflect.full.createInstance



fun parseAction(jobj : JsonObject) {
    val action = Class.forName(jobj.get("action").asString).kotlin
    val instance = action.createInstance()
    if(instance is Action) {

        if (instance.beforeAction(jobj.get("extras").asJsonObject)) {
            instance.doAction(jobj.get("extras").asJsonObject)
        }
    }
}

fun layoutGson() : Gson {
    val adapter = ProteusTypeAdapterFactory(BaseApplication.context)
    val gson = GsonBuilder().registerTypeAdapterFactory(adapter).create()
    return gson;
}

fun Context.readFromAsset(file : String)  :String {
    var json_string = "";
    this.assets.open(file).apply {
        json_string = this.readBytes().toString(Charsets.UTF_8)
    }.close()
    return json_string
}