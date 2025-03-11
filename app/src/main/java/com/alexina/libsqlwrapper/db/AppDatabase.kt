package com.alexina.libsqlwrapper.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.entities.Partner

@Database(entities = [Bill::class, Partner::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun billDao(): BillDao

}