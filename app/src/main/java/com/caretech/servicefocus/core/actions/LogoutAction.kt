package com.caretech.servicefocus.core.actions

import com.caretech.servicefocus.MainActivity
import com.caretech.servicefocus.activity.HomeActivity
import com.caretech.servicefocus.core.Action
import com.caretech.servicefocus.core.BaseApplication
import com.google.gson.JsonObject
import com.parse.ParseUser
import org.jetbrains.anko.startActivity

class LogoutAction : Action {
    override fun beforeAction(params: JsonObject): Boolean = true

    override fun doAction(params: JsonObject): JsonObject {
        ParseUser.logOutInBackground {
            if (it == null) {
                BaseApplication.context.startActivity<MainActivity>()
                HomeActivity.act.finish()
            }
        }

        return JsonObject()
    }

    override fun afterAction(params: JsonObject) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}