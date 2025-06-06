package com.app.saffron.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.app.saffron.data.database.dao.FlowerDao
import com.app.saffron.data.models.FlowerDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database (
    entities = [
        FlowerDto::class
    ],
    version = 1,
    exportSchema = true
)
@TypeConverters(DBConvertors::class)

@ConstructedBy(AppDbConstructor::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun flowerDao(): FlowerDao

    companion object {
        const val DB_NAME = "saffron.db"
    }
}

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDbConstructor: RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

//class DatabaseFactory(private val builder: RoomDatabase.Builder<AppDatabase>) {
//    fun getDatabase(): AppDatabase {
//        return builder
//            .fallbackToDestructiveMigration(dropAllTables = true)
//            .setDriver(BundledSQLiteDriver())
//            .setQueryCoroutineContext(Dispatchers.IO)
//            .build()
//    }
//}

//expect fun getDatabaseBuilder(): AppDatabase
