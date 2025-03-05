package com.alexina.libsqlwrapper.libsql

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.alexina.libsqlwrapper.logW
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import tech.turso.libsql.Connection
import tech.turso.libsql.EmbeddedReplicaDatabase
import tech.turso.libsql.Libsql
import tech.turso.libsql.Rows

class LibsqlRoomDriver(
    context: Context,
    private val databaseDispatcher: CoroutineDispatcher
) : SupportSQLiteOpenHelper {

    companion object {
        const val LIBSQL_DB_NAME = "local.db"
    }

    val dbPath = context.getDatabasePath(LIBSQL_DB_NAME).absolutePath // Use app's private database directory,
    private lateinit var libsqlDb: EmbeddedReplicaDatabase
    private lateinit var connection: Connection

    init {
        // Initialize on the database thread
        runBlocking(databaseDispatcher) {
            libsqlDb = Libsql.open(
                path = dbPath,
                url = "http://10.0.2.2:8080",
                authToken = "",
            )// Initialize your libsql connection
            connection = libsqlDb.connect()
            logW("LibsqlRoomDriver", "Initialize libsqlDb. Thread(${Thread.currentThread().name})")
        }
    }

//    private val libsqlDb: EmbeddedReplicaDatabase = Libsql.open(
//        path = dbPath,
//        url = "http://10.0.2.2:8080",
//        authToken = "",
//    )// Initialize your libsql connection

    override val databaseName: String
        get() = LIBSQL_DB_NAME
    override val readableDatabase: SupportSQLiteDatabase
        get() = LibsqlSupportDatabase(libsqlDb, dbPath, databaseDispatcher)
    override val writableDatabase: SupportSQLiteDatabase
        get() = LibsqlSupportDatabase(libsqlDb, dbPath, databaseDispatcher)

    override fun close() {
        runBlocking(databaseDispatcher) {
            libsqlDb.close()
        }
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    fun syncDatabase(){
        runBlocking(databaseDispatcher) {
            logW("LibsqlRoomDriver", "|||||||||||>>>>>>>>>>> Sync Database <<<<<<<<<<<<<|||||||||||\nThread(${Thread.currentThread().name})")
            libsqlDb.sync()
        }
    }

    fun testQuery(query: String): Rows{
       return libsqlDb.connect().use { connection ->
            connection.query(query)
       }
    }

    // Implement other methods (e.g., setWriteAheadLoggingEnabled)
}