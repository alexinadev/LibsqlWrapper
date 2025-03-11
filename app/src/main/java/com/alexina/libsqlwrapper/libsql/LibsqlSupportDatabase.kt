package com.alexina.libsqlwrapper.libsql

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteTransactionListener
import android.os.CancellationSignal
import android.util.Pair
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteStatement
import com.alexina.libsqlwrapper.logI
import tech.turso.libsql.EmbeddedReplicaDatabase
import java.util.Locale

class LibsqlSupportDatabase(
    private val db: EmbeddedReplicaDatabase,
    override val path: String?
) : SupportSQLiteDatabase {

    private var inTransaction = false
    private var transactionSuccessful = false
    private val TAG = this::class.java.simpleName

//    private val handler = Handler(Looper.getMainLooper()) // Or use a background thread

    override fun query(query: SupportSQLiteQuery): Cursor {
        return query(query.sql, emptyArray())
    }

    override fun query(query: SupportSQLiteQuery, cancellationSignal: CancellationSignal?): Cursor {
        return query(query.sql, emptyArray())
    }

    override fun query(query: String): Cursor {
        return query(query, emptyArray())
    }

    override fun query(query: String, bindArgs: Array<out Any?>): Cursor {
        logI(TAG, "query: $query")
        return db.connect().use { c ->
            val rows = c.query(query, *bindArgs)
            LibsqlCursor(rows)
        }
    }


    override fun setForeignKeyConstraintsEnabled(enabled: Boolean) {
//        execSQL("PRAGMA foreign_keys = ${if (enabled) 1 else 0}")
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
    override var version: Int
        get() = 1
        set(value) {}

    override fun beginTransaction() {
//        db.connect().execute("BEGIN TRANSACTION")
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
        db.close()
    }

    override fun compileStatement(sql: String): SupportSQLiteStatement {
        return LibsqlStatement(db, sql)
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
        inTransaction = false
        transactionSuccessful = false
    }

    override fun execSQL(sql: String) {
        db.connect().use { c -> c.query(sql) }
    }

    override fun execSQL(sql: String, bindArgs: Array<out Any?>) {
        db.connect().use { c -> c.query(sql, bindArgs) }
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