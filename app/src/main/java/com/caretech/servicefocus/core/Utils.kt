package com.caretech.servicefocus.core

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import co.zsmb.materialdrawerkt.draweritems.badge
import co.zsmb.materialdrawerkt.draweritems.badgeable.PrimaryDrawerItemKt
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import java.io.InputStream


fun PrimaryDrawerItemKt.badge(text: String) {
    badge(text) {
        cornersDp = 10
        color = 0xFF0099FF
        textColor = 0xFFFFFFFF
        colorPressed = 0xFFCC99FF
    }
}


fun Context.icon(icon: IIcon, color: Int = Color.WHITE, size: Int = 14): Drawable {
    return IconicsDrawable(this)
            .icon(icon)
            .sizeDp(size)
            .color(color)

}

fun Context.roundIcon(icon: String, color: Int, bgColor: Int, size: Int = 80) : Drawable {
    return IconicsDrawable(this)
            .icon(icon)
            .sizeDp(size)
            .paddingDp(8)
            .backgroundColor(bgColor)
            .color(color)
            .roundedCornersDp(size / 2);
}

fun Context.icon(icon: String, color: Int = Color.WHITE, size: Int = 14): Drawable {
    return IconicsDrawable(this)
            .icon(icon)
            .sizeDp(size)
            .color(color)
}

fun Context.startActivity(actionClass: String) {
    startActivity(Intent(this, Class.forName(actionClass)))
}

@SuppressLint("ResourceType")
fun Context.color(@ColorInt id: Int): Int = ContextCompat.getColor(this, id)

@SuppressLint("ResourceType")
fun Context.drawable(@DrawableRes id: Int): Int = ContextCompat.getColor(this, id)


fun Context.readJSONFromAsset(jsonFileName: String): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = assets.open("layout/$jsonFileName.json")
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}
