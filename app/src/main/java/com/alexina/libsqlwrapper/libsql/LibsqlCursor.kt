package com.alexina.libsqlwrapper.libsql

import android.database.AbstractCursor
import tech.turso.libsql.Rows

class LibsqlCursor(private val rows: Rows) : AbstractCursor() {
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

    override fun getString(column: Int): String = getRow()[column] as String
    override fun getShort(column: Int) = getRow()[column] as Short
    override fun getInt(column: Int) = getRow()[column] as Int
    override fun getLong(column: Int): Long = getRow()[column] as Long
    override fun getFloat(column: Int) = getRow()[column] as Float
    override fun getDouble(column: Int) = getRow()[column] as Double
    override fun isNull(column: Int) = getRow()[column] as Boolean
    override fun getBlob(column: Int): ByteArray = getRow()[column] as ByteArray
    private fun getRow() = rows.next()


    // Close rows when the cursor is closed
    override fun close() {
        rows.close()
        super.close()
    }
}