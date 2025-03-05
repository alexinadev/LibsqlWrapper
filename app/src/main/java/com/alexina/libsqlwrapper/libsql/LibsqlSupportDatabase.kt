package com.alexina.libsqlwrapper.libsql

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteTransactionListener
import android.os.CancellationSignal
import android.util.Pair
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteQuery
import androidx.sqlite.db.SupportSQLiteStatement
import tech.turso.libsql.EmbeddedReplicaDatabase
import java.util.Locale

class LibsqlSupportDatabase(libsqlDb: EmbeddedReplicaDatabase) : SupportSQLiteDatabase {

    private val connection = libsqlDb.connect()



    override fun query(query: SupportSQLiteQuery): Cursor {
//        val rows = connection.execute(query.sql)
        val rows = connection.query(query.sql)
        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor
    }

    override fun query(query: SupportSQLiteQuery, cancellationSignal: CancellationSignal?): Cursor {
        val rows = connection.query(query.sql)
        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor
    }

    override fun query(query: String): Cursor {
        val rows = connection.query(query)
        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor
    }

    override fun query(query: String, bindArgs: Array<out Any?>): Cursor {
        val rows = connection.query(query, bindArgs)
        return LibsqlCursor(rows) // Convert libsql Rows to a Cursor
    }

    override fun setForeignKeyConstraintsEnabled(enabled: Boolean) {
        //"Not yet implemented"
    }

    override fun setLocale(locale: Locale) {
        //"Not yet implemented"
    }

    override fun setMaxSqlCacheSize(cacheSize: Int) {
        //"Not yet implemented"
    }

    override fun setMaximumSize(numBytes: Long): Long {
        //"Not yet implemented"
    }

    override fun setTransactionSuccessful() {
        //"Not yet implemented"
    }

    override fun update(table: String, conflictAlgorithm: Int, values: ContentValues, whereClause: String?, whereArgs: Array<out Any?>?): Int {
        throw Error("Not supported update")
    }

    override fun yieldIfContendedSafely(): Boolean {
        throw Error("Not supported yieldIfContendedSafely")
    }

    override fun yieldIfContendedSafely(sleepAfterYieldDelayMillis: Long): Boolean {
        throw Error("Not supported yieldIfContendedSafely")
    }

    override val attachedDbs: List<Pair<String, String>>?
        get() = TODO("Not yet implemented")
    override val isDatabaseIntegrityOk: Boolean
        get() = TODO("Not yet implemented")
    override val isDbLockedByCurrentThread: Boolean
        get() = TODO("Not yet implemented")
    override val isOpen: Boolean
        get() = TODO("Not yet implemented")
    override val isReadOnly: Boolean
        get() = TODO("Not yet implemented")
    override val isWriteAheadLoggingEnabled: Boolean
        get() = TODO("Not yet implemented")
    override val maximumSize: Long
        get() = TODO("Not yet implemented")
    override var pageSize: Long
        get() = TODO("Not yet implemented")
        set(value) {}
    override val path: String?
        get() = TODO("Not yet implemented")
    override var version: Int
        get() = TODO("Not yet implemented")
        set(value) {}

    override fun beginTransaction() {
        TODO("Not yet implemented")
    }

    override fun beginTransactionNonExclusive() {
        TODO("Not yet implemented")
    }

    override fun beginTransactionWithListener(transactionListener: SQLiteTransactionListener) {
        TODO("Not yet implemented")
    }

    override fun beginTransactionWithListenerNonExclusive(transactionListener: SQLiteTransactionListener) {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun compileStatement(sql: String): SupportSQLiteStatement {
        TODO("Not yet implemented")
    }

    override fun delete(table: String, whereClause: String?, whereArgs: Array<out Any?>?): Int {
        TODO("Not yet implemented")
    }

    override fun disableWriteAheadLogging() {
        TODO("Not yet implemented")
    }

    override fun enableWriteAheadLogging(): Boolean {
        TODO("Not yet implemented")
    }

    override fun endTransaction() {
        TODO("Not yet implemented")
    }

    override fun execSQL(sql: String) {
        TODO("Not yet implemented")
    }

    override fun execSQL(sql: String, bindArgs: Array<out Any?>) {
        TODO("Not yet implemented")
    }

    override fun inTransaction(): Boolean {
        TODO("Not yet implemented")
    }

    override fun insert(table: String, conflictAlgorithm: Int, values: ContentValues): Long {
        // Implement using libsql's executeUpdate
    }

    override fun needUpgrade(newVersion: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete(table: String, whereClause: String?, whereArgs: Array<Any?>?): Int {
        // Delegate to libsql
    }

    // Implement other methods (update, execSQL, etc.)
}