package com.alexina.libsqlwrapper.libsql

import android.util.Log
import androidx.sqlite.db.SupportSQLiteStatement
import com.alexina.libsqlwrapper.logD
import com.alexina.libsqlwrapper.logE
import tech.turso.libsql.Connection
import tech.turso.libsql.EmbeddedReplicaDatabase

class LibsqlStatement(private val db: EmbeddedReplicaDatabase, private val sql: String) : SupportSQLiteStatement {
    override fun execute() {
        db.connect().use { c -> c.query(sql) }
    }

    override fun executeUpdateDelete(): Int = 0

    override fun executeInsert(): Long {
        return 0L
    }

    override fun simpleQueryForLong(): Long {
        return db.connect().use { c -> c.query(sql).next()[0] as Long }
    }

    override fun simpleQueryForString(): String {
        return db.connect().use { c -> c.query(sql).next()[0] as String }

    }

    override fun bindNull(index: Int) = bind(index, null)
    override fun bindLong(index: Int, value: Long) = bind(index, value)
    override fun bindDouble(index: Int, value: Double) = bind(index, value)
    override fun bindString(index: Int, value: String) = bind(index, value)
    override fun clearBindings() {
    }

    override fun close() {
        db.close()
    }

    override fun bindBlob(index: Int, value: ByteArray) = bind(index, value)

    private fun bind(index: Int, value: Any?) {

        // Implement parameter binding if libsql supports prepared statements
        // This might require creating a new query string with parameters
    }
}