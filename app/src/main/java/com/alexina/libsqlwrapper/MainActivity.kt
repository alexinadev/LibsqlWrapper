package com.alexina.libsqlwrapper

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexina.libsqlwrapper.databinding.ActivityMainBinding
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun logD(tag: String, message: String) {
    Log.d("ALEXINA", "[$tag] $message")
}

fun logE(tag: String, message: String) {
    Log.e("ALEXINA", "[$tag] $message")
}

fun logW(tag: String, message: String) {
    Log.w("ALEXINA", "[$tag] $message")
}

fun logI(tag: String, message: String) {
    Log.i("ALEXINA", "[$tag] $message")
}

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var userDao: BillDao

    private val adapterBills by lazy {
        AdapterBills()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateDb.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
            logW(TAG, "***** create database ******\nThread(${Thread.currentThread().name})")
            db = AppDatabase.create(this@MainActivity)
            userDao = db.billDao()
//            }
        }

        binding.btnSyncDb.setOnClickListener {
            logW(TAG, "***** sync database ******")
            (db.openHelper as LibsqlRoomDriver).syncDatabase()
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterBills
        }

        binding.btnGetBills.setOnClickListener {
            logD(TAG, "get bills\nThread(${Thread.currentThread().name})")
            adapterBills.submitList(userDao.getBillsAsync())
        }

    }
}
