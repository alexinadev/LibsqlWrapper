package com.alexina.libsqlwrapper.libsql

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.alexina.libsqlwrapper.logE
import com.alexina.libsqlwrapper.logI
import com.alexina.libsqlwrapper.logW
import tech.turso.libsql.Connection
import tech.turso.libsql.EmbeddedReplicaDatabase
import tech.turso.libsql.Libsql
import tech.turso.libsql.Rows
import java.util.concurrent.Executor
import java.util.concurrent.FutureTask

class LibsqlRoomDriver(
    context: Context,
//    private val databaseExecutor: Executor
) : SupportSQLiteOpenHelper {
    companion object {
        const val LIBSQL_DB_NAME = "local.db"
    }

    private val TAG = this::class.java.simpleName

    private val dbPath = context.getDatabasePath(LIBSQL_DB_NAME).absolutePath

    private val db  = Libsql.open(
        path = dbPath,
        url = "http://192.168.0.101:8080",
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
//        get() {
//            throw Error("Not supported writableDatabase")
//        }

    override fun close() {
        db.close()
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    fun syncDatabase() {
        db.sync()
    }
}