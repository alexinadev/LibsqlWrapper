package com.alexina.libsqlwrapper

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.util.useCursor
import com.alexina.libsqlwrapper.databinding.ActivityMainBinding
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.vm.ViewModelMain
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val viewModel by viewModels<ViewModelMain>()

    private val adapterBills by lazy {
        AdapterBills()
    }
    private val adapterPartners by lazy {
        AdapterPartners()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnCreateDb.setOnClickListener {
            viewModel.sync()
        }

        binding.btnSyncDb.setOnClickListener {

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


        viewModel.billsLiveData.observe(this@MainActivity) {
            adapterBills.submitList(it)
        }
        viewModel.partnersLiveData.observe(this@MainActivity) {
            adapterPartners.submitList(it)
        }

    }
}
