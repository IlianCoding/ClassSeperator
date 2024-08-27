package com.app.assignmentandroidapplication.model.configuration.layoutStrategy

import com.app.assignmentandroidapplication.model.Desk
import com.app.assignmentandroidapplication.model.Position
import com.app.assignmentandroidapplication.model.Student
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.DifferentSortingOptions
import com.app.assignmentandroidapplication.model.configuration.sortingOptions.SortingOption
import com.app.assignmentandroidapplication.utils.LogHelper
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.LocalDate
import kotlin.random.Random

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class RowByRowLayoutStrategyTest {
    @Mock
    private lateinit var logHelper: LogHelper
    @InjectMocks
    private lateinit var strategyTest: RowByRowLayoutStrategy

    private lateinit var desks: List<Desk>
    private lateinit var students: List<Student>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        desks = (1..25).map {
            Desk(it.toString(), Position(it / 5, it % 5))
        }
        students = (1..25).map {
            Student(it.toString(), "FirstName$it", "LastName$it", "Country${Random.nextInt(1, 5)}", "", LocalDate.now(), false)
        }
    }

    @Test
    fun testSuccessfulAssignmentWithAllSortingOptionsEnabled() {
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_SAME_NATIONALITY,
                SortingOption.AVOID_ADJACENT_REPETITION,
                SortingOption.AVOID_SAME_SPOT_REPETITION
            )
        )

        val success = strategyTest.assignStudents(students, desks, sortingOptions, null)

        assertTrue(success)
        assertTrue(desks.all { it.assignedStudent != null })
    }

    @Test
    fun testSuccessfulAssignmentWithOnlyAvoidSameNationalityEnabled() {
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_SAME_NATIONALITY
            )
        )

        val success = strategyTest.assignStudents(students, desks, sortingOptions, null)

        assertTrue(success)
        assertTrue(desks.all { it.assignedStudent != null })
    }

    @Test
    fun testSuccessfulAssignmentWithOnlyAvoidAdjacentRepetitionEnabled() {
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_ADJACENT_REPETITION
            )
        )

        val success = strategyTest.assignStudents(students, desks, sortingOptions,null)

        assertTrue(success)
        assertTrue(desks.all { it.assignedStudent != null })
    }

    @Test
    fun testSuccessfulAssignmentWithOnlyAvoidSameSpotRepetitionEnabled() {
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_ADJACENT_REPETITION
            )
        )

        val success = strategyTest.assignStudents(students, desks, sortingOptions, null)

        assertTrue(success)
        assertTrue(desks.all { it.assignedStudent != null })
    }

    @Test
    fun testAssignmentFailureWhenAvoidSameNationalityIsEnabledAndStudentsHaveSameNationality() {
        // Adjusting the students to all have the same nationality
        val modifiedStudents = (1..25).map {
            Student(it.toString(), "FirstName$it", "LastName$it", "SameCountry", "", LocalDate.now(), false)
        }
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_SAME_NATIONALITY
            )
        )

        val success = strategyTest.assignStudents(modifiedStudents, desks, sortingOptions, null)

        assertFalse(success)
    }

    @Test
    fun testAssignmentFailureWhenAvoidAdjacentRepetitionIsEnabledAndStudentsRepeat() {
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_ADJACENT_REPETITION
            )
        )

        // Make sure the same student is repeated
        val modifiedStudents = students + students[0]
        val success = strategyTest.assignStudents(modifiedStudents, desks, sortingOptions, null)

        assertFalse(success)
    }

    @Test
    fun testAssignmentFailureWhenAvoidSameSpotRepetitionIsEnabledAndStudentsRepeat() {
        val sortingOptions = DifferentSortingOptions(
            selectedOptions = listOf(
                SortingOption.AVOID_ADJACENT_REPETITION
            )
        )

        // Add a student with the same ID to force repetition
        val modifiedStudents = students + students[0]
        val success = strategyTest.assignStudents(modifiedStudents, desks, sortingOptions, null)

        assertFalse(success)
    }
}