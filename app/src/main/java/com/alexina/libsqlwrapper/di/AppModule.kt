package com.alexina.libsqlwrapper.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver.Companion.LIBSQL_DB_NAME
import com.alexina.libsqlwrapper.logI
import com.alexina.libsqlwrapper.logW
import com.alexina.libsqlwrapper.repositories.RepositoryMain
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TAG = "AppModule"

    @Singleton
    @Provides
    fun provideLibsqlRoomDriver(@ApplicationContext context: Context): LibsqlRoomDriver {
        logW(TAG, "⏭⏭⏭ provideLibsqlRoomDriver ⏮⏮⏮")
        return LibsqlRoomDriver(context)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context, libsqlRoomDriver: LibsqlRoomDriver): AppDatabase {
        logW(TAG, "⏭⏭⏭ provideDatabase ⏮⏮⏮")
        logI(TAG, "create database. Thread(${Thread.currentThread().name})")
        libsqlRoomDriver.syncDatabase()
        return Room.databaseBuilder(context, AppDatabase::class.java, LIBSQL_DB_NAME)
            .openHelperFactory { libsqlRoomDriver }
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    logI(TAG, "onOpen. Thread(${Thread.currentThread().name})")
                }
            })
            .build()


    }

    @Singleton
    @Provides
    fun provideBillDao(appDatabase: AppDatabase) = appDatabase.billDao()


    @Singleton
    @Provides
    fun provideRepositoryMain(billDao: BillDao) = RepositoryMain(billDao)


}