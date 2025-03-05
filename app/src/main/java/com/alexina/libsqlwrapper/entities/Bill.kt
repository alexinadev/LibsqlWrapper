package com.alexina.libsqlwrapper.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bill(
    @PrimaryKey val billId: String,
    val v: String,
    val partnerId: String,
    val creator: String,
    val credit: Long = 0,
    val deleted: Boolean = false,
    val type: String,
    val description: String?,
    val createdAt: String?,
    val photo: String?,
    val sms: Boolean = false,
    val connectingId: String? = null,
    val localId: String? = null,
)