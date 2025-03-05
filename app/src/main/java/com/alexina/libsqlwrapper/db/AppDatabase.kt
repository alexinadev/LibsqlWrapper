package com.alexina.libsqlwrapper.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver.Companion.LIBSQL_DB_NAME
import com.alexina.libsqlwrapper.logI

@Database(entities = [Bill::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao

    companion object {
        fun create(context: Context): AppDatabase {
            logI("AppDatabase", "create database. Thread(${Thread.currentThread().name})")
            val openHelper = LibsqlRoomDriver(context)
            openHelper.syncDatabase()
            return Room.databaseBuilder(context, AppDatabase::class.java, LIBSQL_DB_NAME)
                .openHelperFactory { openHelper }
                .build()
        }
    }
}