package com.alexina.libsqlwrapper.repositories

import com.alexina.libsqlwrapper.db.dao.BillDao
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class RepositoryMain @Inject constructor(
    private val billDao: BillDao
) {

    fun getBillsFlow() = billDao.getBills()
    fun getBillsAsync() = billDao.getBillsAsync()


}