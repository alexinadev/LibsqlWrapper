package com.alexina.libsqlwrapper.libsql

import android.database.AbstractCursor
import tech.turso.libsql.Row
import tech.turso.libsql.Rows
import java.util.concurrent.Executor
import java.util.concurrent.FutureTask

class LibsqlCursor(
    private val rows: Rows,
    private val databaseExecutor: Executor
) : AbstractCursor() {
    override fun getCount(): Int {
        val future = FutureTask {
            rows.count() // If available, or iterate to count
        }
        databaseExecutor.execute(future)
        return future.get()
    }

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

    override fun getString(column: Int): String {
        val future = FutureTask {
            getRow()[column] as String
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun getShort(column: Int): Short {
        val future = FutureTask {
            getRow()[column] as Short
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun getInt(column: Int): Int {
        val future = FutureTask {
            getRow()[column] as Int
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun getLong(column: Int): Long {
        val future = FutureTask {
            getRow()[column] as Long
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun getFloat(column: Int): Float {
        val future = FutureTask {
            getRow()[column] as Float
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun getDouble(column: Int): Double {
        val future = FutureTask {
            getRow()[column] as Double
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun isNull(column: Int): Boolean {
        val future = FutureTask {
            getRow()[column] as Boolean
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    override fun getBlob(column: Int): ByteArray {
        val future = FutureTask {
            getRow()[column] as ByteArray
        }
        databaseExecutor.execute(future)
        return future.get()
    }
    private fun getRow(): Row {
        val future = FutureTask {
            rows.next()
        }
        databaseExecutor.execute(future)
        return future.get()
    }

    // Close rows when the cursor is closed
    override fun close() {
        rows.close()
        super.close()
    }
}