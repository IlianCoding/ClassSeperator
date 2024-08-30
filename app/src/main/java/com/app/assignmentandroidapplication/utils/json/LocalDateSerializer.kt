package com.app.assignmentandroidapplication.utils.json

import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type

class LocalDateSerializer : JsonSerializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: com.google.gson.JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(formatter))
    }
}