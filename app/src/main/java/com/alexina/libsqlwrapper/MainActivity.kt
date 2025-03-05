package com.alexina.libsqlwrapper

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexina.libsqlwrapper.databinding.ActivityMainBinding
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.vm.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private val viewModel by viewModels<ViewModelMain>()

    private lateinit var binding: ActivityMainBinding

    private val adapterBills by lazy {
        AdapterBills()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCreateDb.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                logW(TAG, "***** create database ******\nThread(${Thread.currentThread().name})")
//                db = AppDatabase.create(this@MainActivity)
//                userDao = db.billDao()
//            }
        }

        binding.btnSyncDb.setOnClickListener {
//            logW(TAG, "***** sync database ******")
//            (db.openHelper as LibsqlRoomDriver).syncDatabase()
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterBills
        }

        binding.btnGetBills.setOnClickListener {
            logD(TAG, "get bills\nThread(${Thread.currentThread().name})")
//            val rows = (db.openHelper as LibsqlRoomDriver).testQuery("SELECT * FROM Bill")
//            logI(TAG, "rows count: ${rows.count()}")
//
//            rows.forEachIndexed { index, anies ->
//                logD(TAG, "[$index] - ${anies.joinToString(",")}")
//            }

//
//            CoroutineScope(Dispatchers.Default).launch {
//                userDao.getBills().collectLatest { bills ->
//                    withContext(Dispatchers.Main){
//                        adapterBills.submitList(bills)
//                    }
//                }
////                adapterBills.submitList(userDao.getBillsAsync())
//            }

            viewModel.getBills()

        }
    }
}