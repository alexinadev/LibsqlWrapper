package com.alexina.libsqlwrapper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexina.libsqlwrapper.databinding.ActivityMainBinding
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.vm.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<ViewModelMain>()

    private val adapterBills by lazy {
        AdapterBills()
    }
    private val adapterPartners by lazy {
        AdapterPartners()
    }

    lateinit var db: AppDatabase
    lateinit var dao: BillDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnCreateDb.setOnClickListener {
//            initDatabaseRaw()
            viewModel.syncDb()
        }

        binding.btnSyncDb.setOnClickListener {
//            syncDatabaseRaw()

            binding.rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = adapterPartners
            }
            viewModel.getPartners()
        }


        binding.btnGetBills.setOnClickListener {
//            getBillsRaw()
            binding.rv.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = adapterBills
            }
            viewModel.getBills()
        }


        viewModel.billsLiveData.observe(this@MainActivity){
            adapterBills.submitList(it)
        }
        viewModel.partnersLiveData.observe(this@MainActivity){
            adapterPartners.submitList(it)
        }

    }

    private fun getBillsRaw() {
        lifecycleScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
            val b = dao.getBillsAsync()
            val count = b.count()
            val duration = System.currentTimeMillis() - startTime
            withContext(Dispatchers.Main) {
                adapterBills.submitList(b)
                Toast.makeText(this@MainActivity, "$count Bills Fetched in $duration ms", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun syncDatabaseRaw() {
        lifecycleScope.launch(Dispatchers.IO) {
            val startTime = System.currentTimeMillis()
            (db.openHelper as LibsqlRoomDriver).syncDatabase()
            val duration = System.currentTimeMillis() - startTime
            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Database Synced in $duration ms", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initDatabaseRaw() {
//        lifecycleScope.launch(Dispatchers.IO) {
//            val startTime = System.currentTimeMillis()
//            db = AppDatabase.create(this@MainActivity)
//            dao = db.billDao()
//            val duration = System.currentTimeMillis() - startTime
//            withContext(Dispatchers.Main) {
//                Toast.makeText(this@MainActivity, "Database Created in $duration ms", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}