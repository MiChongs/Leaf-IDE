package io.github.caimucheng.leaf.common.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AppContext : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}