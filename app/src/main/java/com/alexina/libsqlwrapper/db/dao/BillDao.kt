package com.alexina.libsqlwrapper.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.entities.Partner
import kotlinx.coroutines.flow.Flow

@Dao
interface BillDao {
    @Query("SELECT * FROM Bill")
    fun getBills(): Flow<List<Bill>>

    @Query("SELECT * FROM Bill")
    fun getBillsAsync(): List<Bill>


    @Query("SELECT * FROM Partner")
    fun getPartnersFlow(): Flow<List<Partner>>

    @Query("SELECT * FROM Partner")
    fun getPartners(): List<Partner>
}