package com.app.saffron

import android.app.Application
import com.app.saffron.di.platformModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import com.app.saffron.di.initKoin
import org.koin.core.logger.Level // Import Level

class SaffronApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger(Level.DEBUG) // Or Level.INFO, Level.ERROR

            androidContext(this@SaffronApplication)

            modules(platformModule)
        }
    }
}