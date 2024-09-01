package com.app.assignmentandroidapplication.utils.gson

import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type

class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: com.google.gson.JsonDeserializationContext?): LocalDate {
        return LocalDate.parse(json?.asString, formatter)
    }
}