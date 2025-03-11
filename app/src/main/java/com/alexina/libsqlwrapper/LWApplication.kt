package com.alexina.libsqlwrapper

import android.app.Application
import com.alexina.libsqlwrapper.di.AppDatabaseEntryPoint
import com.alexina.libsqlwrapper.di.AppModule
import dagger.hilt.EntryPoints
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class LWApplication: Application() {


    private val TAG = this::class.java.simpleName
    private val appScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        // Initialize the database in the background
        appScope.launch {
            val database = EntryPoints.get(this@LWApplication, AppDatabaseEntryPoint::class.java)
                .getDatabase()
            logI(TAG, "\n█████▓▓▓▓▒▒▒░░░░░░░░░░░░░░░░░░▒▒▒▓▓▓▓█████\n" +
                        "█████ Database initialized: $database\n" +
                        "█████▓▓▓▓▒▒▒░░░░░░░░░░░░░░░░░░▒▒▒▓▓▓▓█████"
            )
        }
    }

}