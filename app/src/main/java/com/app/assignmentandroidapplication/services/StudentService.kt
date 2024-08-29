package com.app.assignmentandroidapplication.services

import com.app.assignmentandroidapplication.model.Classroom
import com.app.assignmentandroidapplication.model.configuration.layoutStrategy.LayoutStrategy
import com.app.assignmentandroidapplication.model.configuration.layoutType.LayoutType
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import com.app.assignmentandroidapplication.utils.LogHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout

class StudentService(
    private val layoutStrategies: Map<LayoutType, LayoutStrategy>
) {
    private val logger: LogHelper = LogHelper(this::class.java.simpleName)

    suspend fun assignStudents(classroom: Classroom, sortingOptions: DifferentSortingOptions, groupSize: Int? = null): String {
        logger.d("Starting the student assignment.")

        return withContext(Dispatchers.IO) {
            try {
                withTimeout(120_000L) {
                    // Use the map to get the correct strategy
                    val strategy = layoutStrategies[classroom.layoutType]
                        ?: return@withTimeout "Layout strategy not found."

                    if (classroom.students.size > classroom.desks.size) {
                        val message = "The classroom is too small for the number of students!"
                        logger.e(message)
                        return@withTimeout message
                    }

                    val success = strategy.assignStudents(classroom.students, classroom.desks, sortingOptions, groupSize)
                    val resultMessage = if (success) {
                        "Students assigned successfully!"
                    } else {
                        "No valid seating arrangement found. Consider adjusting sorting options."
                    }
                    logger.i(resultMessage)
                    resultMessage
                }
            } catch (e: TimeoutCancellationException) {
                val message = "The algorithm took too long to find a solution. Please adjust the sorting options."
                logger.e(message)
                message
            } catch (e: Exception) {
                val message = "An unexpected error occurred: ${e.message}"
                logger.e(message)
                message
            }
        }
    }
}
