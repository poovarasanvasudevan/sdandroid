package com.caretech.servicefocus.core

import com.google.gson.JsonObject

interface Action {
    fun beforeAction(params: JsonObject): Boolean
    fun doAction(params: JsonObject): JsonObject
    fun afterAction(params: JsonObject)
}