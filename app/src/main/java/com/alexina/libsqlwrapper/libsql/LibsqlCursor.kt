package com.alexina.libsqlwrapper.libsql

import android.database.AbstractCursor
import tech.turso.libsql.Rows

class LibsqlCursor(private val rows: Rows) : AbstractCursor() {

    private val rowsArray = rows.toList()
    private val colNames = (0 until rows.columnCount)
        .map { index -> rows.columnNames(index)!! }
        .toTypedArray()

    override fun getCount(): Int = rows.count()

    override fun getColumnNames(): Array<String> = colNames

    override fun getString(column: Int): String = getColumn(column) as String
    override fun getShort(column: Int): Short = getColumn(column) as Short
    override fun getInt(column: Int): Int = getLong(column).toInt()
    override fun getLong(column: Int): Long = getColumn(column) as Long
    override fun getFloat(column: Int): Float = getColumn(column) as Float
    override fun getDouble(column: Int): Double = getColumn(column) as Double
    override fun isNull(column: Int): Boolean = getColumn(column) == null
    override fun getBlob(column: Int): ByteArray = getColumn(column) as ByteArray

    private fun getRow() = rowsArray[position]
    private fun getColumn(column: Int): Any? = getRow()[column]

    override fun close() {
        rows.close()
        super.close()
    }
}
