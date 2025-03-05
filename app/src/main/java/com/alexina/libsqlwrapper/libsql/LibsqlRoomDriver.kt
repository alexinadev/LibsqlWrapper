package com.alexina.libsqlwrapper.libsql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteTransactionListener
import android.os.CancellationSignal
import android.util.Pair
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteStatement
import tech.turso.libsql.EmbeddedReplicaDatabase
import tech.turso.libsql.Libsql
import java.util.Locale
import kotlin.Throws
import kotlin.jvm.Throws

class LibsqlRoomDriver(context: Context) : SupportSQLiteOpenHelper {
    val dbPath = context.getDatabasePath("local.db").absolutePath // Use app's private database directory,
    private val libsqlDb: EmbeddedReplicaDatabase = Libsql.open(
        path = dbPath,
        url = "http://10.0.2.2:8080",
        authToken = "",
    )// Initialize your libsql connection

    override val databaseName: String
        get() = "local.db"
    override val readableDatabase: SupportSQLiteDatabase
        get() = LibsqlSupportDatabase(libsqlDb)
    override val writableDatabase: SupportSQLiteDatabase
        //        get() = LibsqlSupportDatabase(libsqlDb)
        get() {
            throw Error("Not supported writableDatabase")
        }

    override fun close() {
        libsqlDb.close()
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    // Implement other methods (e.g., setWriteAheadLoggingEnabled)
}