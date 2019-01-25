package com.mcxiaoke.koi.utils

import android.os.Build

/**
 * User: mcxiaoke
 * Date: 16/1/26
 * Time: 17:45
 */

fun beforeOreo(): Boolean = isOlderVersionThen(26)
fun oreoOrNewer(): Boolean = isOnVersionOrNewer(26)
fun beforeNougat(): Boolean = isOlderVersionThen(24)
fun nougatOrNewer(): Boolean = isOnVersionOrNewer(24)
fun beforeMarshmallow(): Boolean = isOlderVersionThen(23)
fun marshmallowOrNewer(): Boolean = isOnVersionOrNewer(23)
fun beforeLollipop(): Boolean = isOlderVersionThen(21)
fun lollipopOrNewer(): Boolean = isOnVersionOrNewer(21)
fun beforeKitkat(): Boolean = isOlderVersionThen(19)
fun kitkatOrNewer(): Boolean = isOnVersionOrNewer(19)
fun beforeIcs(): Boolean = isOlderVersionThen(14)
fun icsOrNewer(): Boolean = isOnVersionOrNewer(14)
fun beforeVersion(version: Int): Boolean = isOlderVersionThen(version)
fun versionOrNewer(version: Int): Boolean = isOnVersionOrNewer(version)
fun currentVersion(): Int = Build.VERSION.SDK_INT

private fun isOlderVersionThen(version: Int) = Build.VERSION.SDK_INT < version

private fun isOnVersionOrNewer(version: Int) = Build.VERSION.SDK_INT >= version
