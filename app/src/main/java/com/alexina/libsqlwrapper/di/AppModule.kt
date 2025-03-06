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
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val TAG = "AppModule"

    @Singleton
    @Provides
    fun provideDatabaseExecutor(): Executor {
        return Executors.newSingleThreadExecutor { runnable ->
            Thread(runnable, "RoomDatabaseThread").apply {
                isDaemon = true
            }
        }
    }


    @Singleton
    @Provides
    fun provideLibsqlRoomDriver(
        @ApplicationContext context: Context,
        databaseExecutor: Executor
    ): LibsqlRoomDriver {
        val future = FutureTask<LibsqlRoomDriver> {
            logW(TAG, "⏭⏭⏭ Start provideLibsqlRoomDriver ⏮⏮⏮")
            LibsqlRoomDriver(context)
        }
        databaseExecutor.execute(future)
        val driver = future.get() // Block until initialization is complete

        logW(TAG, "⏭⏭⏭ provideLibsqlRoomDriver ⏮⏮⏮")
        return driver
    }

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        libsqlRoomDriver: LibsqlRoomDriver,
        databaseExecutor: Executor
    ): AppDatabase {
        logW(TAG, "⏭⏭⏭ provideDatabase ⏮⏮⏮")
        logI(TAG, "create database. Thread(${Thread.currentThread().name})")
        libsqlRoomDriver.syncDatabase()
        return Room.databaseBuilder(context, AppDatabase::class.java, LIBSQL_DB_NAME)
            .openHelperFactory { libsqlRoomDriver }
            .setQueryExecutor(databaseExecutor) // Use the same executor
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