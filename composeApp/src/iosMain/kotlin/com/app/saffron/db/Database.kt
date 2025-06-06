package com.app.saffron.db

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.app.saffron.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): AppDatabase {
    val dbFile = "${NSHomeDirectory()}/mrx_note.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFile,
        factory = { AppDatabase::class }
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}