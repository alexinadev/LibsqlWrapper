package com.alexina.libsqlwrapper

import android.util.Log

object Utils {
}


fun logD(tag: String, message: String){
    Log.d("ALEXINA", "[$tag] $message")
}
fun logE(tag: String, message: String){
    Log.e("ALEXINA", "[$tag] $message")
}
fun logW(tag: String, message: String){
    Log.w("ALEXINA", "[$tag] $message")
}
fun logI(tag: String, message: String){
    Log.i("ALEXINA", "[$tag] $message")
}
