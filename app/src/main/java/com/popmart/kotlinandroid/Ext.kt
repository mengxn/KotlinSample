package com.popmart.kotlinandroid

import android.content.Context
import android.util.Log
import android.widget.Toast

/**
 * Created by mengxn on 2017/6/6.
 */
fun Context.toast(text:String?) {
    Toast.makeText(this, text?:"null", Toast.LENGTH_SHORT).show()
}

fun Context.toast(vararg args: String) {
    var text = ""
    for (s in args) {
        text = text.plus(s)
    }
    logd(text)
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.logd(text: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, text?:"null")
    }
}

fun Context.logd(resId: Int) {
    logd(getString(resId))
}

fun Context.loge(text: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, text?:"null")
    }
}

fun Context.loge(resId: Int) {
    loge(getString(resId))
}