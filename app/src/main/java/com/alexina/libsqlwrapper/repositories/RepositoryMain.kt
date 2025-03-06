package com.alexina.libsqlwrapper.repositories

import com.alexina.libsqlwrapper.db.dao.BillDao
import com.alexina.libsqlwrapper.entities.Bill
import com.alexina.libsqlwrapper.logD
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ActivityRetainedScoped
class RepositoryMain @Inject constructor(
    private val billDao: BillDao
) {

    private val TAG = this::class.java.simpleName

    fun getBillsFlow(): Flow<List<Bill>> {
        logD(TAG, "getBillsFlow...")
        return billDao.getBills()
    }
    fun getBillsAsync() = billDao.getBillsAsync()


}