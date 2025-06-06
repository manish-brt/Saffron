package com.app.saffron.data.database

import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import platform.Foundation.NSHomeDirectory

actual fun getDatabaseBuilder(): AppDatabase {
    val dbFilePath = "${NSHomeDirectory()}/saffron.db"
    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
//        factory = { AppDatabase::class.instantiateImpl() }
    )
        .fallbackToDestructiveMigration(true)
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}