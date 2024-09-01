package com.app.assignmentandroidapplication.utils.gson

import android.content.Context
import com.app.assignmentandroidapplication.utils.LogHelper
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.IOException

class GsonWriterReader(
    private val context: Context,
    private val gson: Gson
) {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    private fun getFile(fileName: String): File {
        return File(context.filesDir, fileName)
    }

    fun readDataFromFile(fileName: String): Map<String, Any> {
        val file = getFile(fileName)
        return if (file.exists()) {
            try {
                val content = file.readText()
                logger.d("Reading from file: $fileName")
                gson.fromJson(content, object : TypeToken<Map<String, Any>>() {}.type)
            } catch (e: IOException) {
                logger.e("Failed to read from file: $fileName", throwable = e)
                mapOf()
            }
        } else {
            logger.w("File does not exist: $fileName")
            mapOf()
        }
    }

    fun writeDataToFile(fileName: String, data: Map<String, Any>) {
        val file = getFile(fileName)
        try {
            val content = gson.toJson(data)
            file.writeText(content)
            logger.d("Writing to file: $fileName")
        } catch (e: IOException) {
            logger.e("Failed to write to file: $fileName", throwable = e)
        }
    }
}