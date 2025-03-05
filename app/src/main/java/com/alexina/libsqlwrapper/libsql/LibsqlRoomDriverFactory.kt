package com.alexina.libsqlwrapper.libsql

import android.content.Context
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.alexina.libsqlwrapper.logD
import kotlinx.coroutines.CoroutineDispatcher

class LibsqlRoomDriverFactory(
    private val context: Context,
    private val databaseDispatcher: CoroutineDispatcher
) : SupportSQLiteOpenHelper.Factory {
    override fun create(configuration: SupportSQLiteOpenHelper.Configuration): SupportSQLiteOpenHelper {
        logD(this::class.java.simpleName, "create driver on Thread(${Thread.currentThread().name})")
        val driver = LibsqlRoomDriver(context, databaseDispatcher)
        driver.syncDatabase()
        return driver
    }
}