package com.alexina.libsqlwrapper.db

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.entities.Partner
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver.Companion.LIBSQL_DB_NAME
import com.alexina.libsqlwrapper.logI

@Database(entities = [Bill::class, Partner::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        var TABLES = arrayOf("bill", "partner")
    }

    abstract fun billDao(): BillDao


    @SuppressLint("RestrictedApi")
    fun sync(tableNames: Array<String>?) {
        (openHelper as LibsqlRoomDriver).sync()
        invalidationTracker.notifyObserversByTableNames(*(tableNames ?: TABLES))
        Log.d("AppDatabase", "notifyObserversByTableNames")
    }
}