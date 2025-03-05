package com.alexina.libsqlwrapper.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.repositories.RepositoryMain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelMain @Inject constructor(
    private val repositoryMain: RepositoryMain
) : BaseVM() {

    val billsLiveData = MutableLiveData<List<Bill>>(listOf())

    fun getBills() = viewModelScope.launch(Dispatchers.IO){
        repositoryMain.getBillsFlow().collectLatest { bills->
            billsLiveData.postValue(bills)
        }
    }


}