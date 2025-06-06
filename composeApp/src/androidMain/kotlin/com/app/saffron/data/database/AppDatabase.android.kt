package com.app.saffron.data.database

import android.content.Context
import androidx.room.Room
import com.app.saffron.data.database.AppDatabase
import kotlinx.coroutines.Dispatchers

//actual fun getDatabaseBuilder(ctx: Context): AppDatabase {
//    val context = ctx
//    val dbFile = ctx.getDatabasePath(AppDatabase.DB_NAME)
//
//    return Room.databaseBuilder(
//        context = context,
//        klass = AppDatabase::class.java,
//        name = dbFile.absolutePath
//    )
//        .fallbackToDestructiveMigration(true)
//        .setQueryCoroutineContext(Dispatchers.IO)
//        .build()
//}