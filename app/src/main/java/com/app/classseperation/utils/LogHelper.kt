package com.app.classseperation.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogHelper(
    private val tag: String
) {
    private fun getCurrentTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
        return sdf.format(Date())
    }

    private fun formatMessage(message: String, context: String? = null): String {
        val timestamp = getCurrentTimestamp()
        return "[${timestamp}] ${context ?: ""} $message"
    }

    fun v(message: String, context: String? = null) {
        Log.v(tag, formatMessage(message, context))
    }

    fun d(message: String, context: String? = null) {
        Log.d(tag, formatMessage(message, context))
    }

    fun i(message: String, context: String? = null) {
        Log.i(tag, formatMessage(message, context))
    }

    fun w(message: String, context: String? = null) {
        Log.w(tag, formatMessage(message, context))
    }

    fun e(message: String, context: String? = null, throwable: Throwable? = null) {
        Log.e(tag, formatMessage(message, context), throwable)
    }
}