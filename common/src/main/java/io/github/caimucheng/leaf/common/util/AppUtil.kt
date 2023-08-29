package io.github.caimucheng.leaf.common.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.core.content.edit
import java.io.IOException
import kotlin.system.exitProcess

const val LAUNCH_MODE = "launch_mode"

inline val Context.sharedPreferences: SharedPreferences
    get() {
        return getSharedPreferences("Settings", Context.MODE_PRIVATE)
    }

fun Context.launchMode(): String {
    return sharedPreferences.getString(LAUNCH_MODE, null) ?: exitProcess(1)
}

fun ComponentActivity.setupApp(launchMode: String, targetClass: Class<out Activity>) {
    sharedPreferences.edit {
        putString(LAUNCH_MODE, launchMode)
    }

    startActivity(Intent(this, targetClass))
    finish()
}

fun ComponentActivity.setupRootApp(
    launchMode: String,
    targetClass: Class<out Activity>,
    onError: () -> Unit
) {
    try {
        val process = Runtime.getRuntime().exec("su -c /system/bin/id -u")
        process.waitFor()

        process.outputStream.use {
            process.inputStream.use {
                // "0\n".equals("0\n")
                if (process.inputStream.readBytes().contentEquals(byteArrayOf(48, 10))) {
                    setupApp(launchMode, targetClass)
                } else {
                    onError()
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
        onError()
    }
}