package com.alexina.libsqlwrapper.libsql

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteTransactionListener
import android.os.CancellationSignal
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Pair
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteStatement
import com.alexina.libsqlwrapper.logD
import com.alexina.libsqlwrapper.logI
import kotlinx.coroutines.android.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import tech.turso.libsql.EmbeddedReplicaDatabase
import java.util.Locale

class LibsqlSupportDatabase(private val libsqlDb: EmbeddedReplicaDatabase, private val dbPath: String) : SupportSQLiteDatabase {
    private val connection = libsqlDb.connect()
    private var inTransaction = false
    private var transactionSuccessful = false
    private val TAG = this::class.java.simpleName


    override fun query(query: SupportSQLiteQuery): Cursor {
        return query(query.sql)
    }

    override fun query(query: SupportSQLiteQuery, cancellationSignal: CancellationSignal?): Cursor {
        return query(query.sql)
    }

    override fun query(query: String): Cursor {
        logI(TAG, "query: $query\nThread(${Thread.currentThread().name})")

//        return if (Looper.myLooper() == handler.looper) {
        val rows = connection.query(query)
        return LibsqlCursor(rows) // C
//        } else {
//            throw IllegalStateException("Database accessed from wrong thread")
//        }
//        val rows = connection.query(query)
//        logD(TAG, "rows: $rows")
//        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor

//        val rows = libsqlDb.connect().query(query)
//        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor
    }

    override fun query(query: String, bindArgs: Array<out Any?>): Cursor {
        val preparedQuery = bindArgs.foldIndexed(query) { index, acc, _ ->
            acc.replace("?", "\$${index + 1}")
        }
        logI(TAG, "query: $query\npreparedQuery: $preparedQuery\nbindArgs: ${bindArgs.joinToString(",")}")
        val rows = libsqlDb.connect().use { connection ->
            connection.query(preparedQuery, bindArgs)
        }
//        val rows = connection.query(query, bindArgs)
        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor
    }

    override fun setForeignKeyConstraintsEnabled(enabled: Boolean) {
        execSQL("PRAGMA foreign_keys = ${if (enabled) 1 else 0}")
    }

    override fun setLocale(locale: Locale) {
        //"Not yet implemented"
    }

    override fun setMaxSqlCacheSize(cacheSize: Int) {
        //"Not yet implemented"
    }

    override fun setMaximumSize(numBytes: Long): Long {
        //"Not yet implemented"
        return numBytes
    }

    override fun setTransactionSuccessful() {
        transactionSuccessful = true
    }

    override fun update(table: String, conflictAlgorithm: Int, values: ContentValues, whereClause: String?, whereArgs: Array<out Any?>?): Int {
//        val setClause = values.keySet().joinToString(", ") { "$it = ?" }
//        val sql = "UPDATE $table SET $setClause${whereClause?.let { " WHERE $it" } ?: ""}"
//        val args = values.values().toMutableList().apply {
//            whereArgs?.let { addAll(it) }
//        }
//        return connection.executeUpdate(sql, args.toTypedArray()).affectedRows
        throw Exception("Update is not supported via libsql")
    }

    override fun yieldIfContendedSafely(): Boolean = false
    override fun yieldIfContendedSafely(sleepAfterYieldDelayMillis: Long): Boolean = false

    override val attachedDbs: List<Pair<String, String>>?
        get() = emptyList()  // libsql might not support attached databases
    override val isDatabaseIntegrityOk: Boolean
        get() = true  // Implement proper integrity check if needed
    override val isDbLockedByCurrentThread: Boolean
        get() = true  // libsql's threading model may vary
    override val isOpen: Boolean
        get() = true //connection.isOpen
    override val isReadOnly: Boolean
        get() = true
    override val isWriteAheadLoggingEnabled: Boolean
        get() = false  // Implement if libsql supports WAL
    override val maximumSize: Long
        get() = Long.MAX_VALUE
    override var pageSize: Long
        get() = 4096  // Default page size
        set(value) { /* Not implemented */ }
    override val path: String
        get() = dbPath
    override var version: Int
        get() = 1
        set(value) {}

    override fun beginTransaction() {
        libsqlDb.connect().execute("BEGIN TRANSACTION")
        inTransaction = true
        transactionSuccessful = false
    }

    override fun beginTransactionNonExclusive() {
        beginTransaction()
    }

    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) {
        beginTransaction()
        transactionListener.onBegin()
    }

    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) {
        beginTransactionNonExclusive()
        transactionListener.onBegin()
    }

    override fun close() {
        libsqlDb.close()
    }

    override fun compileStatement(sql: String): SupportSQLiteStatement {
        return LibsqlStatement(libsqlDb.connect(), sql)
    }

    override fun delete(table: String, whereClause: String?, whereArgs: Array<out Any?>?): Int {
        throw Exception("Deletion is not supported via libsql")
    }

    override fun disableWriteAheadLogging() {
        // Implement if libsql supports WAL
    }

    override fun enableWriteAheadLogging(): Boolean {
        return false  // libsql might not support WAL
    }

    override fun endTransaction() {
        when {
            inTransaction && transactionSuccessful -> {
                libsqlDb.connect().execute("COMMIT")
            }

            inTransaction -> {
                libsqlDb.connect().execute("ROLLBACK")
            }
        }
        inTransaction = false
        transactionSuccessful = false
    }

    override fun execSQL(sql: String) {
        libsqlDb.connect().execute(sql)
    }

    override fun execSQL(sql: String, bindArgs: Array<out Any?>) {
//        val preparedQuery = bindArgs.foldIndexed(sql) { index, acc, _ ->
//            acc.replace("?", "\$${index + 1}")
//        }
//        libsqlDb.connect().execute(preparedQuery, bindArgs)
        libsqlDb.connect().execute(sql, bindArgs)
    }

    override fun inTransaction(): Boolean = inTransaction

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long {
        throw Exception("insertion is not supported via libsql")
    }

    override fun needUpgrade(newVersion: Int): Boolean {
//        return version < newVersion
        return false
    }
}