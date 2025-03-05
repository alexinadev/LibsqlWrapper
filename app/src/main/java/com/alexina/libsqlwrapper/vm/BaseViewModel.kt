package com.alexina.libsqlwrapper.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexina.libsqlwrapper.logE
import com.alexina.libsqlwrapper.logW
import kotlinx.coroutines.cancel

open class BaseVM : ViewModel(){

    val TAG = "[${this.javaClass.simpleName}]"
    init {
        logW(TAG, "█████▓▓▓▓▒▒▒░░init: ${this.javaClass.simpleName}░░▒▒▒▓▓▓▓█████")
    }

    override fun onCleared() {
        logE(TAG, "█████▓▓▓▓▒▒▒░░onCleared: ${this.javaClass.simpleName}░░▒▒▒▓▓▓▓█████")
        super.onCleared()
        viewModelScope.cancel()
    }

}