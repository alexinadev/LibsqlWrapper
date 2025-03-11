package com.alexina.libsqlwrapper.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Partner(
        @PrimaryKey val partnerId: String,
        val v: String,
        val creator: String,
        val deleted: Boolean = false,
        val balance: Long = 0,
        val positiveBalance: Long = 0,
        val negativeBalance: Long = 0,
        val name: String?,
        val bookId: String,
        val phone: String?,
        val isRegistered: Boolean = false,
)