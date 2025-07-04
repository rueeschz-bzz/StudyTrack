package ch.zli.studytrack

import org.junit.Assert.*
import org.junit.Test

class TimerUtilsTest {

    @Test
    fun test_time_formatting() {
        val millis = 65_000L
        val result = formatTime(millis)
        assertEquals("01:05", result)
    }

    private fun formatTime(remainingMillis: Long): String {
        val totalSeconds = remainingMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%02d:%02d", minutes, seconds)
    }
}
