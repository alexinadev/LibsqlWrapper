package com.alexina.libsqlwrapper.libsql

import android.util.Log
import androidx.sqlite.db.SupportSQLiteStatement
import com.alexina.libsqlwrapper.logD
import com.alexina.libsqlwrapper.logE
import tech.turso.libsql.Connection

class LibsqlStatement(private val connection: Connection, private val sql: String) : SupportSQLiteStatement {
    override fun execute() {
        connection.execute(sql)
    }

    //not supported!
    override fun executeUpdateDelete(): Int = 0

    override fun executeInsert(): Long {

        //not supported!
//        connection.execute(sql)
//        return connection.lastInsertRowId
        return 0L
    }

    override fun simpleQueryForLong(): Long {
        val result = connection.query(sql)
        return result.next()[0] as Long
    }

    override fun simpleQueryForString(): String {
        val result = connection.query(sql)
        return result.next()[0] as String
    }

    override fun bindNull(index: Int) = bind(index, null)
    override fun bindLong(index: Int, value: Long) = bind(index, value)
    override fun bindDouble(index: Int, value: Double) = bind(index, value)
    override fun bindString(index: Int, value: String) = bind(index, value)
    override fun clearBindings() {
        logE("LibsqlStatement", "clearBindings: ")
    }

    override fun close() {
        connection.close()
    }

    override fun bindBlob(index: Int, value: ByteArray) = bind(index, value)

    private fun bind(index: Int, value: Any?) {
        logD("LibsqlStatement", "Binding $value to $index")

        // Implement parameter binding if libsql supports prepared statements
        // This might require creating a new query string with parameters
    }
}