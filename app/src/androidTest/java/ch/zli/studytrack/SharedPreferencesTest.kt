package ch.zli.studytrack

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SharedPreferencesTest {

    private lateinit var prefs: SharedPreferences

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        prefs = context.getSharedPreferences("StudyPrefs", Context.MODE_PRIVATE)
        prefs.edit().clear().commit()
    }

    @Test
    fun saveAndRetrieveTime() {
        val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        prefs.edit().putLong(today.toString(), 42_000L).apply()

        val saved = prefs.getLong(today.toString(), 0L)
        assertEquals(42_000L, saved)
    }
}
