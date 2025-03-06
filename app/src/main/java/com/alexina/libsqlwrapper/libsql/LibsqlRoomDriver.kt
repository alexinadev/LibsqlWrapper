package com.alexina.libsqlwrapper.libsql

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
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
    private val databaseExecutor: Executor
) : SupportSQLiteOpenHelper {
    companion object {
        const val LIBSQL_DB_NAME = "local.db"
    }

    private val TAG = this::class.java.simpleName
    val dbPath = context.getDatabasePath(LIBSQL_DB_NAME).absolutePath // Use app's private database directory,
//    private val libsqlDb: EmbeddedReplicaDatabase = Libsql.open(
//        path = dbPath,
//        url = "http://10.0.2.2:8080",
//        authToken = "",
//    )// Initialize your libsql connection

//    private val db = LibsqlSupportDatabase(libsqlDb, dbPath)









    private lateinit var libsqlDb: EmbeddedReplicaDatabase
    private lateinit var connection: Connection

    init {
        logI(TAG, "⏭⏭⏭ Start LibsqlRoomDriver ⏮⏮⏮")
//        val future = FutureTask {
            libsqlDb = Libsql.open(
                path = dbPath,
                url = "http://10.0.2.2:8080",
                authToken = "",
            )// Initialize your libsql connection
            connection = libsqlDb.connect()
            logI(TAG, "⏭⏭⏭ libsqlDb connected! ⏮⏮⏮\nCurrent Thread: ${Thread.currentThread().name}")
//        }
//        databaseExecutor.execute(future)
//        future.get() // Block until initialization is complete
        logI(TAG, "⏭⏭⏭ LibsqlRoomDriver Ends ⏮⏮⏮")
    }



    override val databaseName: String
        get() = LIBSQL_DB_NAME
    override val readableDatabase: SupportSQLiteDatabase
        get() = writableDatabase
    override val writableDatabase: SupportSQLiteDatabase
        get() {
            val future = FutureTask<SupportSQLiteDatabase> {
                LibsqlSupportDatabase(libsqlDb, dbPath, databaseExecutor)
            }
            databaseExecutor.execute(future)
            return future.get()
        }
//        get() {
//            throw Error("Not supported writableDatabase")
//        }

    override fun close() {
        val future = FutureTask {
            connection.close()
            libsqlDb.close()
        }
        databaseExecutor.execute(future)
        future.get() // Block until close is complete
    }

    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    fun syncDatabase(){
        val future = FutureTask {
            logW("LibsqlRoomDriver", "|||||||||||>>>>>>>>>>> Sync Database <<<<<<<<<<<<<|||||||||||")
            libsqlDb.sync()
        }
        databaseExecutor.execute(future)
        future.get() // Block until sync is complete
    }

    fun testQuery(query: String): Rows{
       return libsqlDb.connect().use { connection ->
            connection.query(query)
       }
    }

    // Implement other methods (e.g., setWriteAheadLoggingEnabled)
}