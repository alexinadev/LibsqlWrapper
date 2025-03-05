package com.alexina.libsqlwrapper.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexina.libsqlwrapper.entities.Bill
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Query("SELECT * FROM Bill")
    fun getBills(): Flow<List<Bill>>

    @Query("SELECT * FROM Bill")
    fun getBillsAsync(): List<Bill>
}