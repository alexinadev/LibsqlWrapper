package com.alexina.libsqlwrapper.libsql

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.alexina.libsqlwrapper.logW
import tech.turso.libsql.EmbeddedReplicaDatabase
import tech.turso.libsql.Libsql
import tech.turso.libsql.Rows

class LibsqlRoomDriver(context: Context) : SupportSQLiteOpenHelper {
    companion object {
        const val LIBSQL_DB_NAME = "local.db"
    }
    val dbPath = context.getDatabasePath(LIBSQL_DB_NAME).absolutePath // Use app's private database directory,
    private val libsqlDb: EmbeddedReplicaDatabase = Libsql.open(
        path = dbPath,
        url = "http://10.0.2.2:8080",
        authToken = "",
    )// Initialize your libsql connection

    private val db = LibsqlSupportDatabase(libsqlDb, dbPath)

    override val databaseName: String
        get() = LIBSQL_DB_NAME
    override val readableDatabase: SupportSQLiteDatabase
        get() = db
    override val writableDatabase: SupportSQLiteDatabase
                get() = db
//        get() {
//            throw Error("Not supported writableDatabase")
//        }

    override fun close() {
        libsqlDb.close()
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    fun syncDatabase(){
        logW("LibsqlRoomDriver", "|||||||||||>>>>>>>>>>> Sync Database <<<<<<<<<<<<<|||||||||||")
        libsqlDb.sync()
    }

    fun testQuery(query: String): Rows{
       return libsqlDb.connect().use { connection ->
            connection.query(query)
       }
    }

    // Implement other methods (e.g., setWriteAheadLoggingEnabled)
}