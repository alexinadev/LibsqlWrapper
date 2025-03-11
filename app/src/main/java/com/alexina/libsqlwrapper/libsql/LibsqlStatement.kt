package com.alexina.libsqlwrapper.libsql
import androidx.sqlite.db.SupportSQLiteStatement

class LibsqlStatement(private val db: LibsqlSupportDatabase, private val sql: String) : SupportSQLiteStatement {
    override fun execute() {
        db.query(sql)
    }

    override fun executeUpdateDelete(): Int = 0

    override fun executeInsert(): Long {
        return 0L
    }

    override fun simpleQueryForLong(): Long {
        return db.query(sql).getLong(0)
    }

    override fun simpleQueryForString(): String {
        return db.query(sql).getString(0)

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