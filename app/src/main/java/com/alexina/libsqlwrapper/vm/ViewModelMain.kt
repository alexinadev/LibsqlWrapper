package com.alexina.libsqlwrapper.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.RoomDatabase
import com.alexina.libsqlwrapper.db.AppDatabase
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.entities.Partner
import com.alexina.libsqlwrapper.libsql.LibsqlRoomDriver
import com.alexina.libsqlwrapper.repositories.RepositoryMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private val repositoryMain: RepositoryMain,
    private val db: AppDatabase,
) : BaseVM() {

    val billsLiveData = MutableLiveData<List<Bill>>(listOf())
    val partnersLiveData = MutableLiveData<List<Partner>>(listOf())

    fun getBills() = viewModelScope.launch(Dispatchers.IO){

//        val bills = repositoryMain.getBillsAsync()
//        billsLiveData.postValue(bills)

        repositoryMain.getBillsFlow().collectLatest { bills->
            billsLiveData.postValue(bills)
        }
    }

    fun getPartners() = viewModelScope.launch(Dispatchers.IO){
        repositoryMain.getPartnersFlow().collectLatest { partners->
            partnersLiveData.postValue(partners)
        }
//        val partners = repositoryMain.getPartners()
//        partnersLiveData.postValue(partners)
    }



    fun sync(tableNames: Array<String>?) {
        db.sync(tableNames)
    }

    fun sync() {
        sync(null)
    }


}