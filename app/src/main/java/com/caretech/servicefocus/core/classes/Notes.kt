package com.caretech.servicefocus.core.classes

import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("Notes")
class Notes() : ParseObject() {

    fun getTitle(): String? {
        return getString("title")
    }

    fun setTitle(value: String) {
        put("title", value)
    }


    fun getDescription(): String? {
        return getString("description")
    }

    fun setDescription(value: String) {
        put("description", value)
    }


    fun getUser(): ParseUser {
        return getParseUser("user")!!
    }

    fun setUser(value: ParseUser) {
        put("user", value)
    }

    fun getStatus(): Boolean {
        return getBoolean("status")
    }

    fun setStatus(value: Boolean) {
        put("status", value)
    }


    fun getFavorite(): Boolean {
        return getBoolean("favorite")
    }

    fun setFavorite(value: Boolean) {
        put("favorite", value)
    }

}