package com.alexina.libsqlwrapper.libsql

import android.database.AbstractCursor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import tech.turso.libsql.Rows

class LibsqlCursor(
    private val rows: Rows,
    private val databaseDispatcher: CoroutineDispatcher
) : AbstractCursor() {
    override fun getCount(): Int = runBlocking(databaseDispatcher) { rows.count() }// If available, or iterate to count

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

    override fun getString(column: Int): String = runBlocking(databaseDispatcher) { getRow()[column] as String }
    override fun getShort(column: Int) = runBlocking(databaseDispatcher) { getRow()[column] as Short }
    override fun getInt(column: Int) = runBlocking(databaseDispatcher) { getRow()[column] as Int }
    override fun getLong(column: Int): Long = runBlocking(databaseDispatcher) { getRow()[column] as Long }
    override fun getFloat(column: Int) = runBlocking(databaseDispatcher) { getRow()[column] as Float }
    override fun getDouble(column: Int) = runBlocking(databaseDispatcher) { getRow()[column] as Double }
    override fun isNull(column: Int) = runBlocking(databaseDispatcher) { getRow()[column] as Boolean }
    override fun getBlob(column: Int): ByteArray = runBlocking(databaseDispatcher) { getRow()[column] as ByteArray }
    private fun getRow() = runBlocking(databaseDispatcher) { rows.next() }


    // Close rows when the cursor is closed
    override fun close() {
        runBlocking(databaseDispatcher) {
            rows.close()
            super.close()
        }
    }
}