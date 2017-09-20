package com.popmart.kotlinandroid

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mengxn on 2017/6/6.
 */
fun Context.toast(text: String?) {
    Toast.makeText(this, text ?: "null", Toast.LENGTH_SHORT).show()
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
        Log.d(javaClass.simpleName, text ?: "null")
    }
}

fun Context.logd(resId: Int) {
    logd(getString(resId))
}

fun Context.loge(text: String?) {
    if (BuildConfig.DEBUG) {
        Log.d(javaClass.simpleName, text ?: "null")
    }
}

fun Context.loge(resId: Int) {
    loge(getString(resId))
}


private val Long.TIME_MINUTE: Long get() = 60 * 1000
private val Long.TIME_HOUR: Long get() = 60 * 60 * 1000
private val Long.TIME_DAY: Long get() = 24 * 60 * 60 * 1000

// 格式化时间
fun Long.toDefaultFormattedTime(): String {
    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.timeInMillis = Date().time
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val intervalTime = Date().time - this
    if (this - calendar.timeInMillis > 0) {
        // this day
        return when {
            intervalTime < TIME_MINUTE -> "刚刚"
            intervalTime in TIME_MINUTE..TIME_HOUR -> "%d分钟前".format(intervalTime / TIME_MINUTE)
            intervalTime in TIME_HOUR..TIME_DAY -> "%d小时前".format(intervalTime / TIME_HOUR)
            else -> "很久以前"
        }
    } else {
        val nowDay = calendar.get(Calendar.DAY_OF_YEAR)
        calendar.timeInMillis = this
        val intervalDay = nowDay - calendar.get(Calendar.DAY_OF_YEAR)
        return when (intervalDay) {
            1 -> "昨天"
            in 2..7 -> "%d天前".format(intervalDay)
            else -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this)
        }
    }
}

// 格式化时间
fun String.toDefaultFormattedTime(): String {
    val date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(this)
    return date.time.toDefaultFormattedTime()
}
