package com.alexina.libsqlwrapper

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.useCursor
import com.alexina.libsqlwrapper.databinding.ActivityMainBinding
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val adapterBills by lazy {
        AdapterBills()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lateinit var db: AppDatabase
        lateinit var dao: BillDao


        binding.btnCreateDb.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val startTime = System.currentTimeMillis()
                db = AppDatabase.create(this@MainActivity)
                dao = db.billDao()
                val duration = System.currentTimeMillis() - startTime
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Database Created in $duration ms", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnSyncDb.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val startTime = System.currentTimeMillis()
                (db.openHelper as LibsqlRoomDriver).syncDatabase()
                val duration = System.currentTimeMillis() - startTime
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Database Synced in $duration ms", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterBills
        }

        binding.btnGetBills.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val startTime = System.currentTimeMillis()
                val b = dao.getBillsAsync()
//                val c = db.openHelper.readableDatabase.query("select * from bill")
                val count = b.size
                val duration = System.currentTimeMillis() - startTime
                withContext(Dispatchers.Main) {
//                    adapterBills.submitList(bills)
                    Toast.makeText(this@MainActivity, "$count Bills Fetched in $duration ms", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
