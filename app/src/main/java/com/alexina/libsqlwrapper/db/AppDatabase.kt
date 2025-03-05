package com.alexina.libsqlwrapper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver.Companion.LIBSQL_DB_NAME
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriverFactory
import com.alexina.libsqlwrapper.logI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asExecutor

@Database(entities = [Bill::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao

    companion object {
        fun create(context: Context, databaseDispatcher: CoroutineDispatcher): AppDatabase {
            logI("AppDatabase", "create database. Thread(${Thread.currentThread().name})")
//            val openHelper = LibsqlRoomDriver(context, databaseDispatcher)
//            openHelper.syncDatabase()
            return Room.databaseBuilder(context, AppDatabase::class.java, LIBSQL_DB_NAME)
                .openHelperFactory(LibsqlRoomDriverFactory(context, databaseDispatcher))
                .setQueryExecutor(databaseDispatcher.asExecutor()) // Use the same dispatcher
                .build()
        }
    }
}