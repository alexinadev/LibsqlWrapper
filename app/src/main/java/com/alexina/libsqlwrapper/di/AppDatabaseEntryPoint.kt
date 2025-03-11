package com.alexina.libsqlwrapper.di

import com.alexina.libsqlwrapper.db.AppDatabase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface AppDatabaseEntryPoint {
    fun getDatabase(): AppDatabase
}