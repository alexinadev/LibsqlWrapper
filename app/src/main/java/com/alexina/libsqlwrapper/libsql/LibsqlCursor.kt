package com.alexina.libsqlwrapper.libsql

import android.database.AbstractCursor
import com.alexina.libsqlwrapper.logD
import tech.turso.libsql.Row
import tech.turso.libsql.Rows

class LibsqlCursor(private val rows: Rows) : AbstractCursor() {
    val rowsArray = rows.toList()

    override fun getCount(): Int = rows.count() // If available, or iterate to count

    override fun getColumnNames(): Array<String> {
        // If column names are not exposed, track them manually (see earlier solutions)
        //(`billId` TEXT NOT NULL,
        // `v` TEXT NOT NULL,
        // `partnerId` TEXT NOT NULL,
        // `creator` TEXT NOT NULL,
        // `credit` INTEGER NOT NULL,
        // `deleted` INTEGER NOT NULL,
        // `type` TEXT NOT NULL, `description` TEXT, `createdAt` TEXT, `photo` TEXT, `sms` INTEGER NOT NULL, `connectingId` TEXT, `localId` TEXT, PRIMARY KEY(`billId`));
        return arrayOf("billId", "v", "partnerId", "creator", "credit", "deleted", "type", "description", "createdAt", "photo", "sms", "connectingId", "localId")
    }

    private fun getRow(): Row {
        return rowsArray[position]
    }

    private fun getColumn(column: Int): Any? {
        val value = getRow()[column]
        return value
    }

    override fun getString(column: Int): String = getColumn(column) as String
    override fun getShort(column: Int) = getColumn(column) as Short
    override fun getInt(column: Int) = getLong(column).toInt()
    override fun getLong(column: Int): Long = getColumn(column) as Long
    override fun getFloat(column: Int) = getColumn(column) as Float
    override fun getDouble(column: Int) = getColumn(column) as Double
    override fun isNull(column: Int) = getColumn(column) == null
    override fun getBlob(column: Int): ByteArray = getColumn(column) as ByteArray
//    private fun getRow() = rows.next()


    override fun close() {
        rows.close()
        super.close()
    }
}