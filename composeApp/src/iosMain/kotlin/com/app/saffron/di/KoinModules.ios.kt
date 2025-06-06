package com.app.saffron.di

import com.app.saffron.data.database.AppDatabase
import com.app.saffron.db.getDatabaseBuilder
import org.koin.dsl.module


actual fun platformModule() = module {
    single<AppDatabase> { getDatabaseBuilder() }
}