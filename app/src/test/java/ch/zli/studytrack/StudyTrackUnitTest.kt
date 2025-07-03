package ch.zli.studytrack

import org.junit.Assert.assertEquals
import org.junit.Test

class StudyTrackUnitTest {

    @Test
    fun timeFormat_isCorrect() {
        val minutes = 2
        val seconds = 5
        val expected = "02:05"
        val actual = String.format("%02d:%02d", minutes, seconds)
        assertEquals(expected, actual)
    }
}