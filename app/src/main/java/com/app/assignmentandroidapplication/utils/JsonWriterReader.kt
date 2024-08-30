package com.app.assignmentandroidapplication.utils

import android.content.Context
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.IOException

class JsonWriterReader(
    private val context: Context,
    private val jsonSerializer: Json
) {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    private fun getFile(fileName: String): File {
        return File(context.filesDir, fileName)
    }

    fun readFromFile(fileName: String): Map<String, Any> {
        val file = getFile(fileName)
        return if (file.exists()) {
            try {
                val content = file.readText()
                logger.d("Reading from file: $fileName")
                jsonSerializer.decodeFromString(content)
            } catch (e: IOException) {
                logger.e("Failed to read from file: $fileName", throwable = e)
                mapOf()
            }
        } else {
            logger.w("File does not exist: $fileName")
            mapOf()
        }
    }

    fun writeToFile(fileName: String, data: Map<String, Any>) {
        val file = getFile(fileName)
        try {
            val content = jsonSerializer.encodeToString(data)
            file.writeText(content)
            logger.d("Writing to file: $fileName")
        } catch (e: IOException) {
            logger.e("Failed to write to file: $fileName", throwable = e)
        }
    }
}