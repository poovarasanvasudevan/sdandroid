package com.caretech.servicefocus.core.actions

import com.google.gson.JsonObject

class MethodExecutionAction : com.caretech.servicefocus.core.Action {
    override fun beforeAction(params: JsonObject): Boolean = true

    override fun doAction(params: JsonObject): JsonObject {
        //val d = FavoriteButton
       return JsonObject();
    }

    override fun afterAction(params: JsonObject) {
    }
}