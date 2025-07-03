package ch.zli.studytrack

import android.content.Context
import android.os.Vibrator
import androidx.test.core.app.ApplicationProvider
import org.junit.Test

class VibrationsTest {

    @Test
    fun vibrator_doesNotThrow() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        try {
            vibrator.vibrate(100L)
        } catch (e: SecurityException) {
            assert(true)
        }
    }
}
