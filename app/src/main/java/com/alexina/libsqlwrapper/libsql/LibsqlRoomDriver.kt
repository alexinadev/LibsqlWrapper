package com.alexina.libsqlwrapper.libsql

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.alexina.libsqlwrapper.logE
import com.alexina.libsqlwrapper.logI
import tech.turso.libsql.Libsql

class LibsqlRoomDriver(
    context: Context,
) : SupportSQLiteOpenHelper {
    companion object {
        const val LIBSQL_DB_NAME = "local.db"
    }

    private val TAG = this::class.java.simpleName

    private val dbPath = context.getDatabasePath(LIBSQL_DB_NAME).absolutePath

    private val db  = Libsql.open(
        path = dbPath,
        url = "http://10.0.2.2:8080",
        authToken = "",
    )

    override val databaseName: String
        get() = LIBSQL_DB_NAME
    override val readableDatabase: SupportSQLiteDatabase
        get() = writableDatabase
    override val writableDatabase: SupportSQLiteDatabase
        get() {
            return LibsqlSupportDatabase(db, dbPath)
        }

    override fun close() {
        db.close()
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    fun syncDatabase()  {
        logI(TAG, "******** syncDatabase Started ********")
        db.sync()
        logE(TAG, "******** syncDatabase ended ********")
    }
}