package com.caretech.servicefocus.core.actions

import com.caretech.servicefocus.core.BaseApplication
import com.caretech.servicefocus.core.startActivity
import com.google.gson.JsonObject

class ActivityTransitionAction() : com.caretech.servicefocus.core.Action {
    override fun beforeAction(params: JsonObject): Boolean = true


    override fun doAction(params: JsonObject): JsonObject {
        val jobj = JsonObject()
        BaseApplication.context.startActivity(params.get("activity").asString)
        return jobj
    }

    override fun afterAction(params: JsonObject) {
    }

}