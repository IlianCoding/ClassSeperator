package com.app.assignmentandroidapplication.service

import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.configuration.layoutStrategy.LayoutStrategy
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.utils.LogHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class ClassroomService(
    private val layoutStrategies: Map<LayoutType, LayoutStrategy>
) {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    suspend fun assigningStudents(classroom: Classroom) {
        logger.d("Initialising the student assignment process")

        return withContext(Dispatchers.IO) {
            try {
                withTimeout(120_000L) {
                    val strategy = layoutStrategies[classroom.layoutType]
                        ?: return@withTimeout "The selected layout strategy was not found."

                    if (classroom.studentIds.size > classroom.desks.size) {
                        val message = "The classroom is too small for the number of students!"
                        logger.e(message)
                        return@withTimeout message
                    }

                    val success = strategy.assignStudents(classroom)

                    val resultMessage = if (success) {
                        "Students assigned successfully!"
                    } else {
                        "No valid seating arrangement found. Consider adjusting sorting options."
                    }
                    logger.i(resultMessage)
                    resultMessage
                }
            } catch (e: TimeoutCancellationException) {
                val message = "The algorithm took too long to find a solution.\nPlease adjust the sorting options."
                logger.e(message)
            } catch (e : Exception) {
                val message = "An error occurred: ${e.message}"
                logger.e(message)
            }
        }
    }
}